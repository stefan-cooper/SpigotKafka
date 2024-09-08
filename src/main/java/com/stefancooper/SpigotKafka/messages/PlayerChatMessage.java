package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;

public class PlayerChatMessage extends BaseMessage {

    final AsyncPlayerChatEvent chatEvent;

    public PlayerChatMessage(Player player, Date time, AsyncPlayerChatEvent chatEvent) {
        super(player, time);
        this.chatEvent = chatEvent;
    }

    public String getChatMessage() {
        return chatEvent.getMessage();
    }

    @Override
    public JsonObject encodeToJson() {
        final JsonObject json = super.encodeToJson();
        json.addProperty("message", getChatMessage());
        return super.encodeToJson();
    }
}
