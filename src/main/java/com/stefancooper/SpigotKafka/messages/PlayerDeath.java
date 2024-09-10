package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import com.stefancooper.SpigotKafka.resources.Type;

import java.util.Date;

public class PlayerDeath extends Base {

    final PlayerDeathEvent deathEvent;

    public PlayerDeath(Player player, Date time, PlayerDeathEvent deathEvent) {
        super(player, time, Type.DEATH);
        this.deathEvent = deathEvent;
    }

    public String getDeathMessage() {
        return deathEvent.getDeathMessage();
    }

    @Override
    public JsonObject encodeToJson() {
        final JsonObject json = super.encodeToJson();
        json.addProperty("reason", getDeathMessage());
        return json;
    }
}
