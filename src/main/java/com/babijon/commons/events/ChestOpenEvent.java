package com.babijon.commons.events;

import lombok.Getter;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ChestOpenEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private @Getter Chest chest;
    private @Getter Player player;

    public ChestOpenEvent(Chest chest, Player player) {
        this.chest = chest;
        this.player = player;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
