package com.stefancooper.KafkaMinecraft;

import com.google.gson.JsonObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.*;

public class Core implements Listener {

    private Produce kafkaProducer;

    public Core(Produce kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @EventHandler
    public void notifyLogin(PlayerLoginEvent e) {
        JsonObject details = new JsonObject();
        details.addProperty("id", UUID.randomUUID().toString());
        details.addProperty("player", e.getPlayer().getDisplayName());
        details.addProperty("time", new Date().toGMTString());
        // TODO - produce this as a JSON
        kafkaProducer.produceMessage("login", details.toString());
    }

    @EventHandler
    public void notifyDisconnect(PlayerQuitEvent e) {
        JsonObject details = new JsonObject();
        details.addProperty("id", UUID.randomUUID().toString());
        details.addProperty("player", e.getPlayer().getDisplayName());
        details.addProperty("time", new Date().toGMTString());
        // TODO - produce this as a JSON
        kafkaProducer.produceMessage("disconnect", details.toString());
    }

    @EventHandler
    public void notifyDeath(PlayerDeathEvent e) {
        JsonObject details = new JsonObject();
        details.addProperty("id", UUID.randomUUID().toString());
        details.addProperty("player", e.getEntity().getPlayer().getDisplayName());
        details.addProperty("time", new Date().toGMTString());
        // TODO - produce this as a JSON
        kafkaProducer.produceMessage("death", details.toString());
    }

    @EventHandler
    public void notifyChatMessage(AsyncPlayerChatEvent e) {
        JsonObject details = new JsonObject();
        details.addProperty("id", UUID.randomUUID().toString());
        details.addProperty("player", e.getPlayer().getDisplayName());
        details.addProperty("message", e.getMessage());
        details.addProperty("time", new Date().toGMTString());
        // TODO - produce this as a JSON
        kafkaProducer.produceMessage("chat", details.toString());
    }
}
