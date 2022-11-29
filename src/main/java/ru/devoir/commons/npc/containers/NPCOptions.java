package ru.devoir.commons.npc.containers;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Location;

@Builder
@Getter
public class NPCOptions {
    private final String name;
    private final String texture;
    private final String signature;
    private final Location location;
    private final boolean hideNametag;
    private final boolean rotateHead;
}
