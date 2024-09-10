package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import com.stefancooper.SpigotKafka.resources.Type;

import java.util.Date;

public class PlayerRegen extends Base {

    final EntityRegainHealthEvent regenEvent;

    public PlayerRegen(Player player, Date time, EntityRegainHealthEvent regenEvent) {
        super(player, time, Type.REGEN);
        this.regenEvent = regenEvent;
    }

    int getRegenAmount() {
        return (int) regenEvent.getAmount();
    }

    int getNewHealth() { return getPlayerHealth() + getRegenAmount(); }

    @Override
    public JsonObject encodeToJson() {
        final JsonObject json = super.encodeToJson();
        json.addProperty("health", getNewHealth());
        return json;
    }
}
