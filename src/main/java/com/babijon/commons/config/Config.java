package com.babijon.commons.config;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Config {

    private final File file;
    private FileConfiguration configuration;

    private final List<Consumer<FileConfiguration>> reloadActions = new ArrayList<>();

    public Config(Plugin plugin, String name) {

        file = new File(plugin.getDataFolder(), name);
        load();

    }

    private void load() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        load();
        reloadActions.forEach(a -> a.accept(configuration));
    }

    public FileConfiguration get() {
        return configuration;
    }

    @SneakyThrows
    public void save() {
        configuration.save(file);
    }

}
