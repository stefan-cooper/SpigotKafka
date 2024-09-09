package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class Base {

    private final String id;
    private final Player player;
    private final Date time;

    public Base(final Player player, final Date time) {
        this.id = UUID.randomUUID().toString();
        this.player = player;
        this.time = time;
    }

    public String getId () {
        return id;
    }

    public String getPlayerName () {
        return player.getDisplayName();
    }

    public String getTimeString() {
        return time.toString();
    }

    public JsonObject encodeToJson() {
        final JsonObject json = new JsonObject();
        json.addProperty("id", this.getId());
        json.addProperty("player", getPlayerName());
        json.addProperty("time", getTimeString());
        return json;
    }
}
