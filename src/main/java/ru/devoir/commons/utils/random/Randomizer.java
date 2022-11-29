package ru.devoir.commons.utils.random;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Randomizer<T> {
    private HashMap<T, Double> distribution = new HashMap();
    private double distSum;
    private SecureRandom random = new SecureRandom();

    public void addValue(T value, double distribution) {
        if (this.distribution.get(value) != null) {
            this.distSum -= this.distribution.get(value);
        }

        this.distribution.put(value, distribution);
        this.distSum += distribution;
    }

    public T getRandomValue() {
        double rand = this.random.nextDouble();
        double ratio = 1.0D / this.distSum;
        double tempDist = 0.0D;
        Iterator<Entry<T, Double>> var7 = this.distribution.entrySet().iterator();

        Entry entry;
        Object i;
        do {
            if (!var7.hasNext()) {
                throw new IllegalStateException("Randomizator is empty");
            }

            entry = var7.next();
            i = entry.getKey();
        } while(rand / ratio > (tempDist += (Double)entry.getValue()));

        return (T) i;
    }

    public void reset() {
        this.distribution.clear();
    }

}
