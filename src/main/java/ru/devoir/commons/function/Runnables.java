package ru.devoir.commons.function;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.devoir.commons.DevoirPlugin;

public class Runnables {

    private static final Plugin plugin = DevoirPlugin.getInstance();

    public static void runLater(Runnable runnable, long time, boolean async) {
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        if (async)
            bukkitRunnable.runTaskLaterAsynchronously(plugin, time);
        else
            bukkitRunnable.runTaskLater(plugin, time);
    }

    public static void runTask(Runnable runnable, long time, boolean async) {
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        };
        if (async)
            bukkitRunnable.runTaskTimerAsynchronously(plugin, 0L, time);
        else
            bukkitRunnable.runTaskTimer(plugin, 0L, time);
    }

}
