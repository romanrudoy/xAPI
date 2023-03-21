package com.babijon.commons.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import com.babijon.commons.xAPI;

public class AbstractListener implements Listener {

    public AbstractListener() {
        Bukkit.getPluginManager().registerEvents(this, xAPI.getInstance());
    }

}
