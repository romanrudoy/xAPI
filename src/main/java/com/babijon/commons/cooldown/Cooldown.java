package com.babijon.commons.cooldown;

public class Cooldown {

    private final int seconds;
    private final long cooldownTime;

    public Cooldown(int seconds) {
        this.cooldownTime = System.currentTimeMillis();
        this.seconds = seconds;
    }

    public Cooldown(long start, int seconds) {
        this.cooldownTime = start;
        this.seconds = seconds;
    }

    public boolean isCooldown() {
        return this.secondsLeft() > 0L;
    }

    public long secondsLeft() {
        return this.cooldownTime / 1000L + (long)this.seconds - System.currentTimeMillis() / 1000L;
    }

}
