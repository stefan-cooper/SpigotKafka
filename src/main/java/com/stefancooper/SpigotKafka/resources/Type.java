package com.stefancooper.SpigotKafka.resources;

public enum Type {
    LOGIN("login"),
    DISCONNECT("disconnect"),
    DEATH("death"),
    RESPAWN("respawn"),
    REGEN("regen"),
    CHAT("chat"),
    DAMAGE("damage");

    public final String label;

    Type(String label) {
        this.label = label;
    }
}
