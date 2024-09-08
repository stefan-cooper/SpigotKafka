package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Date;

public class PlayerDeathMessage extends BaseMessage {

    final PlayerDeathEvent deathEvent;

    public PlayerDeathMessage(Player player, Date time, PlayerDeathEvent deathEvent) {
        super(player, time);
        this.deathEvent = deathEvent;
    }

    public String getDeathMessage() {
        return deathEvent.getDeathMessage();
    }

    @Override
    public JsonObject encodeToJson() {
        final JsonObject json = super.encodeToJson();
        json.addProperty("reason", getDeathMessage());
        return super.encodeToJson();
    }
}
