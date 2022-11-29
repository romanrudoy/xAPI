package ru.devoir.commons;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.devoir.commons.command.impl.TestCommand;
import ru.devoir.commons.gui.GUIListener;

public class DevoirPlugin extends JavaPlugin {

    private static DevoirPlugin instance;
    private Permission permission;
    private Chat chat;

    @Override
    public void onEnable() {

        instance = this;

        permission = Bukkit.getServicesManager().getRegistration(Permission.class).getProvider();
        chat = Bukkit.getServicesManager().getRegistration(Chat.class).getProvider();

        new GUIListener();

    }

    public static DevoirPlugin getInstance() {
        return instance;
    }

    public Permission getPermission() {
        return permission;
    }

    public Chat getChat() {
        return chat;
    }

}
