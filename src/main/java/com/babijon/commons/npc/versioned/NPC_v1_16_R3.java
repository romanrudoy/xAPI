package com.babijon.commons.npc.versioned;

import com.babijon.commons.npc.NMSHelper;
import com.babijon.commons.npc.containers.NPCOptions;
import com.babijon.commons.npc.containers.interfaces.NPC;
import com.babijon.commons.utils.MessageUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import lombok.SneakyThrows;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R3.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_16_R3.scoreboard.CraftScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class NPC_v1_16_R3 implements NPC {

    private final JavaPlugin plugin;

    private final UUID uuid = UUID.randomUUID();
    private final String name;
    private final String entityName;
    private final String texture;
    private final String signature;
    private final boolean hideNametag;

    private final org.bukkit.inventory.ItemStack helmet;
    private final org.bukkit.inventory.ItemStack mainHand;
    private final org.bukkit.inventory.ItemStack offHand;

    private EntityPlayer entityPlayer;

    public NPC_v1_16_R3(JavaPlugin plugin, NPCOptions npcOptions) {
        this.plugin = plugin;

        this.name = npcOptions.getName();
        this.texture = npcOptions.getTexture();
        this.signature = npcOptions.getSignature();
        this.hideNametag = npcOptions.isHideNametag();

        if (hideNametag) {
            this.entityName = MessageUtil.randomCharacters(10);
        } else {
            this.entityName = this.name;
        }

        this.helmet = npcOptions.getHelmet();
        this.mainHand = npcOptions.getMainHand();
        this.offHand = npcOptions.getOffHand();

        addToWorld(npcOptions.getLocation());

        new BukkitRunnable() {

            @Override
            public void run() {

                for (UUID playerID : viewers) {

                    Player currentlyOnline = Bukkit.getPlayer(playerID);
                    if (currentlyOnline == null ||
                            !currentlyOnline.isOnline()) {
                        continue;
                    }

                    sendEquipment(currentlyOnline);
                    fixSkinHelmetLayerForPlayer(currentlyOnline);

                }

            }
        }.runTaskTimer(plugin, 0L, 20L*30);

        new BukkitRunnable() {

            @Override
            public void run() {

                for (UUID playerID : viewers) {

                    Player currentlyOnline = Bukkit.getPlayer(playerID);
                    if (currentlyOnline == null || !currentlyOnline.isOnline()) {
                        continue;
                    }

                    sendHeadRotationPacket(currentlyOnline);

                }

            }

        }.runTaskTimer(plugin, 0L, 1L);
    }

    private void addToWorld(Location location) {
        if (location.getWorld() == null) {
            throw new IllegalArgumentException("NPC Location world cannot be null");
        }

        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile gameProfile = makeGameProfile();

        PlayerInteractManager interactManager = new PlayerInteractManager(worldServer);
        entityPlayer = new EntityPlayer(
                minecraftServer,
                worldServer,
                gameProfile,
                interactManager
        );

        entityPlayer.setLocation(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

    private GameProfile makeGameProfile() {
        GameProfile gameProfile = new GameProfile(uuid, entityName);
        gameProfile.getProperties().put(
                "textures",
                new Property("textures",
                        texture,
                        signature
                )
        );
        return gameProfile;
    }

    private final Set<UUID> viewers = new HashSet<>();
    public void showTo(Player player) {
        viewers.add(player.getUniqueId());

        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                entityPlayer
        );
        sendPacket(player, packetPlayOutPlayerInfo);

        PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(
                entityPlayer
        );
        sendPacket(player, packetPlayOutNamedEntitySpawn);

        CraftScoreboardManager scoreboardManager = ((CraftServer) Bukkit.getServer()).getScoreboardManager();
        assert scoreboardManager != null;
        CraftScoreboard mainScoreboard = scoreboardManager.getMainScoreboard();
        Scoreboard scoreboard = mainScoreboard.getHandle();

        ScoreboardTeam scoreboardTeam = scoreboard.getTeam(entityName);
        if (scoreboardTeam == null) {
            scoreboardTeam = new ScoreboardTeam(scoreboard, entityName);
        }

        scoreboardTeam.setNameTagVisibility(
                hideNametag ? ScoreboardTeamBase.EnumNameTagVisibility.NEVER : ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS
        );
        scoreboardTeam.setCollisionRule(ScoreboardTeamBase.EnumTeamPush.NEVER);

        if (hideNametag) {
            scoreboardTeam.setColor(EnumChatFormat.GRAY);
            scoreboardTeam.setPrefix(
                    new ChatMessage(ChatColor.COLOR_CHAR + "7[NPC] ")
            );
        }

        sendPacket(player, new PacketPlayOutScoreboardTeam(scoreboardTeam, 1)); // Create team
        sendPacket(player, new PacketPlayOutScoreboardTeam(scoreboardTeam, 0)); // Setup team options
        sendPacket(player, new PacketPlayOutScoreboardTeam(scoreboardTeam, Collections.singletonList(entityName), 3)); // Add entityPlayer to team entries

        sendHeadRotationPacket(player);
        sendEquipment(player);
        fixSkinHelmetLayerForPlayer(player);

        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            try {
                PacketPlayOutPlayerInfo removeFromTabPacket = new PacketPlayOutPlayerInfo(
                        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
                        entityPlayer
                );
                sendPacket(player, removeFromTabPacket);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, 20);

    }

    public void hideFrom(Player player) {
        if (!viewers.contains(player.getUniqueId())) return;
        viewers.remove(player.getUniqueId());

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entityPlayer.getId());
        sendPacket(player, packet);
    }

    @Override
    public void delete() {
        Set<Player> onlineViewers = viewers.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        onlineViewers.forEach(this::hideFrom);
    }

    @SneakyThrows
    public void sendHeadRotationPacket(Player player) {
        Location original = getLocation();

        if (!original.getWorld().getName().equals(player.getWorld().getName())) return;
        if (player.getLocation().distance(original) > 25) return;

        Location location = original.clone().setDirection(player.getLocation().subtract(original.clone()).toVector());

        byte yaw = (byte) (location.getYaw() * 256 / 360);
        byte pitch = (byte) (location.getPitch() * 256 / 360);

        PacketPlayOutEntityHeadRotation headRotationPacket = new PacketPlayOutEntityHeadRotation(
                this.entityPlayer,
                yaw
        );
        sendPacket(player, headRotationPacket);

        PacketPlayOutEntity.PacketPlayOutEntityLook lookPacket = new PacketPlayOutEntity.PacketPlayOutEntityLook(
                getId(),
                yaw,
                pitch,
                false
        );
        sendPacket(player, lookPacket);
    }

    public void fixSkinHelmetLayerForPlayer(Player player) {
        Byte skinFixByte = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
        sendMetadata(player, 16, skinFixByte);
    }

    @SneakyThrows
    private void sendMetadata(Player player, int index, Byte o) {
        DataWatcher dataWatcher = entityPlayer.getDataWatcher();

        DataWatcherSerializer<Byte> registry = DataWatcherRegistry.a;
        dataWatcher.set(
                registry.a(index),
                o
        );

        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(
                getId(),
                dataWatcher,
                false
        );
        sendPacket(player, metadataPacket);
    }

    @SneakyThrows
    private void sendEquipment(Player player) {

        List<Pair<EnumItemSlot, ItemStack>> items = new ArrayList<>();

        items.add(Pair.of(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(helmet)));
        items.add(Pair.of(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(mainHand)));
        items.add(Pair.of(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(offHand)));

        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(entityPlayer.getId(), items);

        sendPacket(player, packet);

    }

    public Location getLocation() {
        return new Location(
                entityPlayer.getWorld().getWorld(),
                entityPlayer.locX(),
                entityPlayer.locY(),
                entityPlayer.locZ(),
                entityPlayer.yaw,
                entityPlayer.pitch
        );
    }

    public int getId() {
        return entityPlayer.getId();
    }

    @Override
    public String getName() {
        return name;
    }

    private void sendPacket(Player player, Object packet) {
        NMSHelper.getInstance().sendPacket(player, packet);
    }

}
