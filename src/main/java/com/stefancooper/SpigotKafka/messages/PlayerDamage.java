package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import com.stefancooper.SpigotKafka.resources.Type;

import java.util.Date;

public class PlayerDamage extends Base {

    final EntityDamageEvent damageEvent;

    public PlayerDamage(Player player, Date time, EntityDamageEvent damageEvent) {
        super(player, time, Type.DAMAGE);
        this.damageEvent = damageEvent;
    }

    int getDamage() {
        return (int) damageEvent.getDamage();
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

    int getNewHealth() { return getPlayerHealth() - getDamage(); }

    @Override
    public JsonObject encodeToJson() {
        final JsonObject json = super.encodeToJson();
        json.addProperty("damage", getDamage());
        json.addProperty("damageSource", getDamageSource());
        json.addProperty("health", getNewHealth());
        return json;
    }
}
