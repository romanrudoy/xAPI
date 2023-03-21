package com.babijon.commons.cooldown;

import com.babijon.commons.xAPI;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import com.babijon.commons.utils.temporal.TemporalUtils;

import java.util.HashMap;
import java.util.Map;

public class Cooldowns {

    private static Map<String, Cooldown> COOLDOWNS = new HashMap<>();

    @SneakyThrows
    public static void load() {
        COOLDOWNS = xAPI.getInstance().getCooldownsDatabase().loadCooldowns();
    }

    @SneakyThrows
    public static void save() {
        xAPI.getInstance().getCooldownsDatabase().saveAllCooldowns(COOLDOWNS);
    }

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
