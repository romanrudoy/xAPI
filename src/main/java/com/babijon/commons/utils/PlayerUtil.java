package com.babijon.commons.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.babijon.commons.xAPI;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class PlayerUtil {

    public List<Player> getPlayersWithPermission(String permission) {
        return Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(permission)).collect(Collectors.toList());
    }

    public List<Player> getNearPlayers(Location location, int radius) {
        return location.getWorld().getPlayers().stream().filter(p -> p.getLocation().distance(location) <= radius)
                .collect(Collectors.toList());
    }

    public String getGroup(String player) {
        return xAPI.getInstance().getLuckPerms().getUserManager().getUser(player).getPrimaryGroup();
    }

    public String getGroup(UUID player) {
        return xAPI.getInstance().getLuckPerms().getUserManager().getUser(player).getPrimaryGroup();
    }

    public boolean isInvFull(Player player) {
        return Arrays.stream(player.getInventory().getStorageContents()).noneMatch(Objects::isNull);
    }

    public boolean giveOrDrop(Player player, ItemStack itemStack) {
        if (isInvFull(player)) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            return false;
        } else {
            player.getInventory().addItem(itemStack);
            return true;
        }
    }

    public static String getIp(Player player) {
        return player.getAddress().getAddress().getHostAddress();
    }

    public static boolean isOnline(String playerName) {
        return Bukkit.getPlayerExact(playerName) != null;
    }

}
