package ru.devoir.commons.gui.impl;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.devoir.commons.gui.MenuItem;
import ru.devoir.commons.gui.PagedGUI;
import ru.devoir.commons.item.ItemUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestPagedMenu extends PagedGUI {

    @Override
    public String getTitle() {
        return "Страничное меню";
    }

    @Override
    public List<String> getMatrix() {
        return Arrays.asList("         ", "  *****  ", "  *****  ", "         ", " {  X  } ", "         ");
    }

    @Override
    public Map< Character, MenuItem> getItems() {

        final Map<Character, MenuItem> itemMap = new HashMap<>();

        itemMap.put('{', new MenuItem(getPreviousItem(), e -> {}));
        itemMap.put('}', new MenuItem(getNextPageItem(), e -> {}));
        itemMap.put('X', new MenuItem(new ItemUtil(Material.STONE_BUTTON).setNamed("§cЗакрыть").build(), e -> player.closeInventory()));

        return itemMap;

    }

    @Override
    public void onFillItemClick(InventoryClickEvent event) {
        player.sendMessage("Вы нажали на материал.");
    }

    @Override
    public List<ItemStack> getFillItems() {
        return Arrays.stream(Material.values()).map(ItemStack::new).collect(Collectors.toList());
    }
}
