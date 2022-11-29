package ru.devoir.commons.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.devoir.commons.npc.containers.NPCOptions;
import ru.devoir.commons.npc.containers.enums.NPCClickAction;
import ru.devoir.commons.npc.containers.enums.ServerVersion;
import ru.devoir.commons.npc.containers.interfaces.NPC;
import ru.devoir.commons.npc.events.NPCInteractionEvent;
import ru.devoir.commons.npc.versioned.NPC_Reflection;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NPCService {

    private final JavaPlugin plugin;
    private final boolean useReflection;

    private final Set<NPC> registeredNPCs = new HashSet<>();

    public NPCService(JavaPlugin plugin, boolean useReflection) {
        this.plugin = plugin;
        this.useReflection = useReflection;

        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, PacketType.Play.Client.USE_ENTITY) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        EnumWrappers.EntityUseAction useAction = event.getPacket().getEntityUseActions().read(0);
                        int entityId = event.getPacket().getIntegers().read(0);
                        handleEntityClick(event.getPlayer(), entityId, NPCClickAction.fromProtocolLibAction(useAction));
                    }
                }
        );
    }

    private final Cache<Player, NPC> clickedNPCCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1L, TimeUnit.SECONDS)
            .build();

    private void handleEntityClick(Player player, int entityId, NPCClickAction action) {
        registeredNPCs.stream()
                .filter(npc -> npc.getId() == entityId)
                .forEach(npc -> Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                    NPC previouslyClickedNPC = clickedNPCCache.getIfPresent(player);
                    if (previouslyClickedNPC != null && previouslyClickedNPC.equals(npc)) return; // If they've clicked this same NPC in the last 0.5 seconds ignore this click
                    clickedNPCCache.put(player, npc);

                    NPCInteractionEvent event = new NPCInteractionEvent(npc, player, action);
                    Bukkit.getPluginManager().callEvent(event);
                }, 2));
    }

    public NPC newNPC(NPCOptions options) {
        ServerVersion serverVersion = NMSHelper.getInstance().getServerVersion();
        NPC npc = null;

        if (useReflection) {
            serverVersion = ServerVersion.REFLECTED;
        }

        if (serverVersion == ServerVersion.REFLECTED) {
            npc = new NPC_Reflection(plugin, options);
        }

        if (npc == null) {
            throw new IllegalStateException("Invalid server version " + serverVersion + ". This plugin needs to be updated!");
        }

        registeredNPCs.add(npc);
        return npc;
    }

    public Optional<NPC> findNPC(String name) {
        return registeredNPCs.stream()
                .filter(npc -> npc.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public void deleteNPC(NPC npc) {
        npc.delete();
        registeredNPCs.remove(npc);
    }

    public void deleteAllNPCs() {
        // Copy the set to prevent concurrent modification exception
        Set<NPC> npcsCopy = new HashSet<>(registeredNPCs);
        npcsCopy.forEach(this::deleteNPC);
    }

}
