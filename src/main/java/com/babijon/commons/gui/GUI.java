package com.babijon.commons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class GUI implements InventoryHolder {

    protected final Map<Integer, Consumer<InventoryClickEvent>> actionMap = new HashMap<>();

    protected Inventory inventory;
    protected Player player;

    public abstract String getTitle();
    public abstract int getSize();

    public abstract void setItems();

    public void onClick(InventoryClickEvent event) {

        if (!actionMap.containsKey(event.getSlot())) return;

        event.setCancelled(true);
        actionMap.get(event.getSlot()).accept(event);

    }

    public void onClose(InventoryCloseEvent event) {

    }

    public void onDrag(InventoryDragEvent event) {

    }

    public void open() {

        this.inventory = Bukkit.createInventory(this, this.getSize(), this.getTitle());
        setItems();

        player.openInventory(inventory);

    }

    public void addItem(ItemStack stack, Consumer<InventoryClickEvent> consumer, int... slots) {
        for (int i : slots) {
            actionMap.put(i, consumer);
            inventory.setItem(i, stack);
        }
    }

    public GUI setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public void copy(MenuTemplate template) {

        actionMap.putAll(template.getActionMap());
        template.getItemMap().forEach((s,i) -> inventory.setItem(s, i));

    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
