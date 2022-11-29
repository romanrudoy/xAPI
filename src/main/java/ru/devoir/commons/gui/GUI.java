package ru.devoir.commons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class GUI implements InventoryHolder {

    protected final Map<Integer, Consumer<InventoryClickEvent>> actionMap = new HashMap<>();

    protected Inventory inventory;
    protected Player player;

    public abstract String getTitle();

    public abstract List<String> getMatrix();
    public abstract Map<Character, MenuItem> getItems();

    public void onClick(InventoryClickEvent event) {

        if (!actionMap.containsKey(event.getSlot())) return;

        event.setCancelled(true);
        actionMap.get(event.getSlot()).accept(event);

    }

    public void onClose(InventoryCloseEvent event) {

    }

    public void open() {

        if (getMatrix().isEmpty() || getMatrix().size() > 6) return;
        inventory = Bukkit.createInventory(this, getMatrix().size() * 9, getTitle());

        String r;
        char c;
        int slot;
        ItemStack itemStack;
        for (int line = 0; line < getMatrix().size(); line++) {

            r = getMatrix().get(line);
            for (int row = 0; row < 9; row++) {

                c = r.charAt(row);
                if (!getItems().containsKey(c)) continue;;

                itemStack = getItems().get(c).getItemStack();
                slot = 9 * line + row;

                inventory.setItem(slot, itemStack);
                actionMap.put(slot, getItems().get(c).getConsumer());

            }
        }

        player.openInventory(inventory);

    }

    public GUI setPlayer(Player player) {
        this.player = player;
        return this;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
