package com.babijon.commons.gui;

import lombok.Getter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MenuTemplate {

    private @Getter Map<Integer, Consumer<InventoryClickEvent>> actionMap;
    private @Getter Map<Integer, ItemStack> itemMap;

    private @Getter int size;
    private @Getter String title;

    public MenuTemplate(int size, String title) {
        this.size = size;
        this.title = title;

        this.actionMap = new HashMap<>();
        this.itemMap = new HashMap<>();
    }

    public void putAction(int slot, Consumer<InventoryClickEvent> eventConsumer) {
        actionMap.put(slot, eventConsumer);
    }

    public void putItem(int slot, ItemStack itemStack) {
        itemMap.put(slot, itemStack);
    }

}
