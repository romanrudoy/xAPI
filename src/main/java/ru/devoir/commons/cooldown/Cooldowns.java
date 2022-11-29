package ru.devoir.commons.cooldown;

import org.bukkit.entity.Player;
import ru.devoir.commons.utils.temporal.TemporalUtils;

import java.util.HashMap;
import java.util.Map;

public class Cooldowns {

    private static final Map<String, Cooldown> COOLDOWNS = new HashMap<>();

    public static void addCooldown(Player player, String action, int time) {
        Cooldown cooldown = new Cooldown(time);
        COOLDOWNS.put(player.getName() + ";" + action, cooldown);
    }

    public static String getLeft(Player player, String action) {

        if (!COOLDOWNS.containsKey(player.getName() + ";" + action)) return null;

        Cooldown cooldown = COOLDOWNS.get(player.getName() + ";" + action);
        if (!cooldown.isCooldown()) return null;

        return TemporalUtils.formatTime(cooldown.secondsLeft() * 1000L);

    }

}
