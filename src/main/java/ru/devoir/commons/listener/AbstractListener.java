package ru.devoir.commons.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import ru.devoir.commons.DevoirPlugin;

public class AbstractListener implements Listener {

    public AbstractListener() {
        Bukkit.getPluginManager().registerEvents(this, DevoirPlugin.getInstance());
    }

}
