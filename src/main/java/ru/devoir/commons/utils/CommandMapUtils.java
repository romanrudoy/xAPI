package ru.devoir.commons.utils;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

public final class CommandMapUtils {
    private static final CommandMap COMMAND_MAP;

    public static CommandMap getMap() {
        return COMMAND_MAP;
    }

    static {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        try {
            COMMAND_MAP = (CommandMap) lookup.findVirtual(Bukkit.getServer().getClass(), "getCommandMap", MethodType.methodType(SimpleCommandMap.class)).invoke(Bukkit.getServer());
        } catch (Throwable var2) {
            throw new RuntimeException(var2);
        }
    }
}

