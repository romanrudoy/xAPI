package ru.devoir.commons.gui.impl;

import org.bukkit.Material;
import ru.devoir.commons.gui.GUI;
import ru.devoir.commons.gui.MenuItem;
import ru.devoir.commons.item.ItemUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMenu extends GUI {

    @Override
    public String getTitle() {
        return "§8Тестовое Меню";
    }

    @Override
    public List<String> getMatrix() {
        return Arrays.asList("         ", "         ", "    X    ", "         ", "         ");
    }

    @Override
    public Map<Character, MenuItem> getItems() {

        final Map<Character, MenuItem> items = new HashMap<>();

        items.put('X', new MenuItem(new ItemUtil(Material.CHORUS_FRUIT).build(), e -> {
            player.sendMessage("Вы нажали на хорус!");
        }));

        return items;

    }
}
