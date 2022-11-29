package ru.devoir.commons.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.devoir.commons.item.ItemUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class PagedGUI extends GUI {

    public abstract void onFillItemClick(InventoryClickEvent event);
    public abstract List<ItemStack> getFillItems();

    protected int page = 0;
    private int index;

    public void fillItems(List<Integer> slots) {

        if (slots.isEmpty()) return;
        if (getFillItems().isEmpty()) return;

        for (int i = 0; i <= slots.size(); i++) {

            index = slots.size() * page + i;
            if (index >= getFillItems().size()) break;

            ItemStack itemStack = getFillItems().get(index);
            for (int slot : slots) {

                if (!checkSlot(inventory, slot)) continue;

                inventory.setItem(slot, itemStack);
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

    @Override
    public void open() {

        if (getMatrix().isEmpty() || getMatrix().size() > 6) return;
        inventory = Bukkit.createInventory(this, getMatrix().size() * 9, getTitle());

        List<Integer> fillSlots = new ArrayList<>();

        String r;
        char c;
        int slot;
        ItemStack itemStack;
        for (int line = 0; line < getMatrix().size(); line++) {

            r = getMatrix().get(line);
            for (int row = 0; row < 9; row++) {

                c = r.charAt(row);
                slot = 9 * line + row;

                if (c == '*') {
                    fillSlots.add(slot);
                    actionMap.put(slot, this::onFillItemClick);
                    continue;
                }

                if (!getItems().containsKey(c)) continue;;
                itemStack = getItems().get(c).getItemStack();

                inventory.setItem(slot, itemStack);
                if (c == '{') {
                    actionMap.put(slot, e -> previousPage());
                } else if (c == '}') {
                    actionMap.put(slot, e -> nextPage());
                } else {
                    actionMap.put(slot, getItems().get(c).getConsumer());
                }

            }
        }

        fillItems(fillSlots);
        player.openInventory(inventory);

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
