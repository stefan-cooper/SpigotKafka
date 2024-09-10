package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Date;

public class PlayerDamage extends Base {

    final EntityDamageEvent damageEvent;

    public PlayerDamage(Player player, Date time, EntityDamageEvent damageEvent) {
        super(player, time);
        this.damageEvent = damageEvent;
    }

    Double getDamage() {
        return damageEvent.getDamage();
    }

    String getDamageSource() {
        Entity damager = damageEvent.getDamageSource().getCausingEntity();
        if (damager == null) {
          return "unknown";
        } else if (damager instanceof HumanEntity) {
            HumanEntity humanDamager = (HumanEntity) damager;
            return humanDamager.getName();
        } else {
            return damager.getType().toString();
        }
    }

    @Override
    public JsonObject encodeToJson() {
        final JsonObject json = super.encodeToJson();
        json.addProperty("damage", getDamage());
        json.addProperty("damageSource", getDamageSource());
        return json;
    }
}
