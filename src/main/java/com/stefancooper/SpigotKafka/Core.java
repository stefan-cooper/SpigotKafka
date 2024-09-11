package com.stefancooper.SpigotKafka;

import com.stefancooper.SpigotKafka.messages.*;
import com.stefancooper.SpigotKafka.resources.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;

import java.util.*;

public class Core implements Listener {

    private final Produce kafkaProducer;

    public Core(Produce kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    // View docs for various events https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/package-summary.html

    @EventHandler
    public void login(PlayerLoginEvent e) {
        Base message = new Base(e.getPlayer(), new Date(), Type.LOGIN);
        kafkaProducer.produceMessage("login", message.encodeToJson().toString());
    }

    @EventHandler
    public void disconnect(PlayerQuitEvent e) {
        Base message = new Base(e.getPlayer(), new Date(), Type.DISCONNECT);
        kafkaProducer.produceMessage("disconnect", message.encodeToJson().toString());
    }

    @EventHandler
    public void death(PlayerDeathEvent e) {
        PlayerDeath message = new PlayerDeath(e.getEntity().getPlayer(), new Date(), e);
        kafkaProducer.produceMessage("death", message.encodeToJson().toString());
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent e) {
        Base message = new Base(e.getPlayer(), new Date(), Type.RESPAWN);
        kafkaProducer.produceMessage("respawn", message.encodeToJson().toString());
    }

    @EventHandler
    public void regen(EntityRegainHealthEvent e) {
        if (e.getEntity().getType().equals(EntityType.PLAYER) && e.getEntity() instanceof Player player) {
            PlayerRegen message = new PlayerRegen(player, new Date(), e);
            kafkaProducer.produceMessage("regen", message.encodeToJson().toString());
        } else {
            // noop, we don't care about regen to non player entities
        }
    }

    @EventHandler
    public void chatMessage(AsyncPlayerChatEvent e) {
        PlayerChat message = new PlayerChat(e.getPlayer(), new Date(), e);
        kafkaProducer.produceMessage("chat", message.encodeToJson().toString());
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent e) {
        Base message = new Base((Player) e.getPlayer(), new Date(), Type.INVENTORY);
        kafkaProducer.produceMessage("inventory", message.encodeToJson().toString());
    }

    @EventHandler
    public void damage(EntityDamageEvent e) {
        if (e.getEntity().getType().equals(EntityType.PLAYER) && e.getEntity() instanceof Player player) {
            PlayerDamage message = new PlayerDamage(player, new Date(), e);
            kafkaProducer.produceMessage("damage", message.encodeToJson().toString());
        } else {
            // noop, we don't care about damage to non player entities
        }
    }

}
