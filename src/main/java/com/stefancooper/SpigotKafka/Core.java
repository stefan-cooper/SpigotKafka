package com.stefancooper.SpigotKafka;

import com.google.gson.JsonObject;
import com.stefancooper.SpigotKafka.messages.BaseMessage;
import com.stefancooper.SpigotKafka.messages.PlayerChatMessage;
import com.stefancooper.SpigotKafka.messages.PlayerDeathMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.*;

public class Core implements Listener {

    private final Produce kafkaProducer;

    public Core(Produce kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @EventHandler
    public void notifyLogin(PlayerLoginEvent e) {
        BaseMessage message = new BaseMessage(e.getPlayer(), new Date());
        kafkaProducer.produceMessage("login", message.encodeToJson().toString());
    }

    @EventHandler
    public void notifyDisconnect(PlayerQuitEvent e) {
        BaseMessage message = new BaseMessage(e.getPlayer(), new Date());
        kafkaProducer.produceMessage("disconnect", message.encodeToJson().toString());
    }

    @EventHandler
    public void notifyDeath(PlayerDeathEvent e) {
        PlayerDeathMessage message = new PlayerDeathMessage(e.getEntity().getPlayer(), new Date(), e);
        kafkaProducer.produceMessage("death", message.encodeToJson().toString());
    }

    @EventHandler
    public void notifyChatMessage(AsyncPlayerChatEvent e) {
        PlayerChatMessage message = new PlayerChatMessage(e.getPlayer(), new Date(), e);
        kafkaProducer.produceMessage("death", message.encodeToJson().toString());
    }
}
