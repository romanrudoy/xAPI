package com.babijon.commons.events;

import com.babijon.commons.listener.AbstractListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomEventsListener extends AbstractListener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.CHEST) return;

        Chest chest = (Chest) event.getClickedBlock();
        ChestOpenEvent openEvent = new ChestOpenEvent(chest, event.getPlayer());

        Bukkit.getPluginManager().callEvent(openEvent);

    }
}
