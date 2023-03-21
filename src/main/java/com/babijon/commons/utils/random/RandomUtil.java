package com.babijon.commons.utils.random;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomUtil {

    public static final RandomUtil IMP = new RandomUtil();
    private static final Random random = new Random();

    public static Random getRandom() {
        return random;
    }

    public static int ramdomInteger(int amount) {
        return random.nextInt(amount);
    }

    public static int ramdomRange(int min, int max) {
        return ramdomInteger(max) + min;
    }

    public <T> T chance(Map<T, Integer> map, int chance) {
        List<T> list = Lists.newArrayList();
        map.forEach((t, integer) -> {
            if (integer >= chance) {
                list.add(t);
            }

        });
        return list.isEmpty() ? null : this.getFromList(list);
    }

    public <T> T getFromArray(T[] array) {
        return array[random.nextInt(array.length)];
    }

    public <T> T getFromList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

}
