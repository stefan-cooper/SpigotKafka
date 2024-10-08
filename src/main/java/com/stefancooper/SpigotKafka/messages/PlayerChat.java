package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import com.stefancooper.SpigotKafka.resources.Type;

import java.util.Date;

public class PlayerChat extends Base {

    final AsyncPlayerChatEvent chatEvent;

    public PlayerChat(Player player, Date time, AsyncPlayerChatEvent chatEvent) {
        super(player, time, Type.CHAT);
        this.chatEvent = chatEvent;
    }

    public String getChatMessage() {
        return chatEvent.getMessage();
    }

    @Override
    public JsonObject encodeToJson() {
        final JsonObject json = super.encodeToJson();
        json.addProperty("message", getChatMessage());
        return json;
    }
}
