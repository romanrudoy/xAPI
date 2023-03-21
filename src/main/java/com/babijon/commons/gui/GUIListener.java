package com.babijon.commons.gui;

import com.babijon.commons.listener.AbstractListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class GUIListener extends AbstractListener {

    @EventHandler
    private void onClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;
        if (!(event.getClickedInventory().getHolder() instanceof GUI)) return;

        GUI gui = (GUI) event.getClickedInventory().getHolder();
        gui.onClick(event);

    }

    @EventHandler
    private void onDrag(InventoryDragEvent event) {

        if (event.getInventory() == null) return;
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        GUI gui = (GUI) event.getInventory().getHolder();
        gui.onDrag(event);

    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {

        if (event.getInventory() == null) return;
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        GUI gui = (GUI) event.getInventory().getHolder();
        gui.onClose(event);

    }

}
