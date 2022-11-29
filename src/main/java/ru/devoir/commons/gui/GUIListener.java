package ru.devoir.commons.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import ru.devoir.commons.listener.AbstractListener;

public class GUIListener extends AbstractListener {

    @EventHandler
    private void onClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;
        if (!(event.getClickedInventory().getHolder() instanceof GUI)) return;

        GUI gui = (GUI) event.getClickedInventory().getHolder();
        gui.onClick(event);

    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {

        if (event.getInventory() == null) return;
        if (!(event.getInventory().getHolder() instanceof GUI)) return;

        GUI gui = (GUI) event.getInventory().getHolder();
        gui.onClose(event);

    }

}
