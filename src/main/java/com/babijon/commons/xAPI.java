package com.babijon.commons;

import com.babijon.commons.cooldown.Cooldowns;
import com.babijon.commons.cooldown.CooldownsDatabase;
import com.babijon.commons.events.CustomEventsListener;
import com.babijon.commons.gui.GUIListener;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class xAPI extends JavaPlugin {

    private static @Getter xAPI instance;
    private @Getter Permission permission;
    private @Getter Chat chat;
    private @Getter LuckPerms luckPerms;

    private @Getter CooldownsDatabase cooldownsDatabase;

    @Override
    public void onEnable() {

        instance = this;
        saveDefaultConfig();

        permission = Bukkit.getServicesManager().getRegistration(Permission.class).getProvider();
        chat = Bukkit.getServicesManager().getRegistration(Chat.class).getProvider();

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        luckPerms = provider.getProvider();

        new GUIListener();
        new CustomEventsListener();

        try {
            cooldownsDatabase = new CooldownsDatabase();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }

        Cooldowns.load();

    }

    @Override
    public void onDisable() {
        Cooldowns.save();
    }

}
