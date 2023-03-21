package com.babijon.commons.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import com.babijon.commons.item.ItemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class PagedGUI extends GUI {

    public abstract void onFillItemClick(InventoryClickEvent event);
    public abstract List<ItemStack> getFillItems();
    public abstract List<Integer> getSlotsToFill();

    protected int page = 0;
    private int index;

    public void fillItems(Consumer<InventoryClickEvent> action) {

        if (getSlotsToFill().isEmpty()) return;
        if (getFillItems().isEmpty()) return;

        for (int i = 0; i <= getSlotsToFill().size(); i++) {

            index = getSlotsToFill().size() * page + i;
            if (index >= getFillItems().size()) break;

            ItemStack itemStack = getFillItems().get(index);
            for (int slot : getSlotsToFill()) {

                if (!checkSlot(inventory, slot)) continue;

                addItem(itemStack, action, slot);
                break;

            }

        }

    }

    public void nextPage() {

        if (((index + 1) < getFillItems().size())) {
            page++;
            open();
        }

    }

    public void previousPage() {
        if (page > 0) {
            page--;
            open();
        }
    }

    protected ItemStack getNextPageItem() {
        return new ItemUtil(Material.ARROW).setNamed("§fСледующая Страница").build();
    }

    protected ItemStack getPreviousItem() {
        return new ItemUtil(Material.ARROW).setNamed("§fПредыдущая Страница").build();
    }

    private boolean checkSlot(Inventory inventory, int i) {
        return inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR;
    }

    @Override
    public GUI setPlayer(Player player) {
        this.player = player;
        return this;
    }

}
