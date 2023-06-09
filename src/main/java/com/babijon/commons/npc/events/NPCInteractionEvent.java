package com.babijon.commons.npc.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.babijon.commons.npc.containers.enums.NPCClickAction;
import com.babijon.commons.npc.containers.interfaces.NPC;

public class NPCInteractionEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final NPC clicked;

    @Getter
    private final Player player;

    @Getter
    private final NPCClickAction clickAction;

    public NPCInteractionEvent(NPC clicked, Player player, NPCClickAction clickAction) {
        this.clicked = clicked;
        this.player = player;
        this.clickAction = clickAction;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}