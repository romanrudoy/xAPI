package ru.devoir.commons.npc.containers.enums;

public enum ServerVersion {
    v1_16_R3,
    REFLECTED,
    UNKNOWN;

    @Override
    public String toString() {
        return name();
    }
}