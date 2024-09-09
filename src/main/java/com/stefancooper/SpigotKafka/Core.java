package com.stefancooper.SpigotKafka;

import com.stefancooper.SpigotKafka.messages.Base;
import com.stefancooper.SpigotKafka.messages.PlayerDamage;
import com.stefancooper.SpigotKafka.messages.PlayerChat;
import com.stefancooper.SpigotKafka.messages.PlayerDeath;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
        Base message = new Base(e.getPlayer(), new Date());
        kafkaProducer.produceMessage("login", message.encodeToJson().toString());
    }

    @EventHandler
    public void disconnect(PlayerQuitEvent e) {
        Base message = new Base(e.getPlayer(), new Date());
        kafkaProducer.produceMessage("disconnect", message.encodeToJson().toString());
    }

    @EventHandler
    public void death(PlayerDeathEvent e) {
        PlayerDeath message = new PlayerDeath(e.getEntity().getPlayer(), new Date(), e);
        kafkaProducer.produceMessage("death", message.encodeToJson().toString());
    }

    @EventHandler
    public void chatMessage(AsyncPlayerChatEvent e) {
        PlayerChat message = new PlayerChat(e.getPlayer(), new Date(), e);
        kafkaProducer.produceMessage("death", message.encodeToJson().toString());
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
