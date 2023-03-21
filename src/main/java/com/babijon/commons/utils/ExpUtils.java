package com.babijon.commons.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public final class ExpUtils {

    public void setTotalExperience(Player player, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Experience is negative!");
        } else {
            player.setExp(0.0F);
            player.setLevel(0);
            player.setTotalExperience(0);
            int amount = exp;

            while(amount > 0) {
                int expToLevel = getExpAtLevel(player);
                amount -= expToLevel;
                if (amount >= 0) {
                    player.giveExp(expToLevel);
                } else {
                    amount += expToLevel;
                    player.giveExp(amount);
                    amount = 0;
                }
            }

        }
    }

    private int getExpAtLevel(Player player) {
        return getExpAtLevel(player.getLevel());
    }

    public int getExpAtLevel(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        } else {
            return level <= 30 ? 5 * level - 38 : 9 * level - 158;
        }
    }

    public int getExpToLevel(int level) {
        int currentLevel = 0;

        int exp;
        for(exp = 0; currentLevel < level; ++currentLevel) {
            exp += getExpAtLevel(currentLevel);
        }

        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }

        return exp;
    }

    public int getTotalExperience(Player player) {
        int exp = Math.round((float)getExpAtLevel(player) * player.getExp());

        for(int currentLevel = player.getLevel(); currentLevel > 0; exp += getExpAtLevel(currentLevel)) {
            --currentLevel;
        }

        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }

        return exp;
    }

    public int getExpUntilNextLevel(Player player) {
        int exp = Math.round((float)getExpAtLevel(player) * player.getExp());
        int nextLevel = player.getLevel();
        return getExpAtLevel(nextLevel) - exp;
    }
}

