package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonObject;
import com.stefancooper.SpigotKafka.resources.Type;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class Base {

    private final String id;
    private final Player player;
    private final Date time;
    private final Type type;

    public Base(final Player player, final Date time, Type type) {
        this.id = UUID.randomUUID().toString();
        this.player = player;
        this.time = time;
        this.type = type;
    }

    public String getId () {
        return id;
    }

    public String getPlayerName () {
        return player.getDisplayName();
    }

    public int getPlayerHealth() { return (int) Math.ceil(player.getHealth()); }

    public String getTimeString() {
        return time.toString();
    }

    public Type getType() { return type; }

    public JsonObject encodeToJson() {
        final JsonObject json = new JsonObject();
        json.addProperty("id", this.getId());
        json.addProperty("player", getPlayerName());
        json.addProperty("health", getPlayerHealth());
        json.addProperty("time", getTimeString());
        json.addProperty("type", type.label);
        return json;
    }
}
