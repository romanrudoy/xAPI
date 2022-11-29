package ru.devoir.commons.utils;

import com.comphenix.packetwrapper.WrapperPlayServerBlockAction;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ChestUtils {

    public static void setChestOpened(Block block, boolean opened) {

        WrapperPlayServerBlockAction packet = new WrapperPlayServerBlockAction();
        packet.setLocation(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        packet.setByte1(1);
        packet.setByte2(opened ? 1 : 0);
        packet.setBlockType(block.getType());

        int distanceSquared = 64 * 64;
        Location loc = block.getLocation();

        for (Player player : block.getWorld().getPlayers()) {
            if (player.getLocation().distanceSquared(loc) < distanceSquared) {
                packet.sendPacket(player);
            }
        }

    }

}
