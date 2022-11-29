package ru.devoir.commons.cooldown;

import org.bukkit.entity.Player;
import ru.devoir.commons.utils.PlayerUtil;

import java.util.HashMap;
import java.util.Map;

public class CooldownBuilder {

    private final int defaultTime;
    private Map<String, Integer> groups = new HashMap<>();

    public CooldownBuilder(int defaultTime) {
        this.defaultTime = defaultTime;
    }

    public CooldownBuilder addGroup(String name, int time) {
        groups.put(name, time);
        return this;
    }

    public int getTime(Player player) {
        String group = PlayerUtil.getGroup(player);
        return groups.getOrDefault(group, defaultTime);
    }

}
