package ru.devoir.commons.command.objects;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandIssuer {

    private final CommandSender base;

    public CommandIssuer(CommandSender base) {
        this.base = base;
    }

    public CommandSender getBase() {
        return base;
    }

    public void sendMessage(String message) {
        base.sendMessage(message);
    }

    public boolean hasPermission(String permission) {
        return base.hasPermission(permission);
    }

    public boolean isPlayer() {
        return base instanceof Player;
    }

}
