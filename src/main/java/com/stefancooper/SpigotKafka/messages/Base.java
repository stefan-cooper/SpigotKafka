package com.stefancooper.SpigotKafka.messages;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.stefancooper.SpigotKafka.resources.Type;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class Base {

    private final String id;
    private final Player player;
    private final Date time;
    private final Type type;

    public static List<Material> InventoryHighlights = List.of(
            Material.GOLDEN_APPLE,
            Material.ENCHANTED_GOLDEN_APPLE,
            Material.DIAMOND_SWORD,
            Material.TNT
    );

    public Base(final Player player, final Date time, Type type) {
        this.id = UUID.randomUUID().toString();
        this.player = player;
        this.time = time;
        this.type = type;
    }

    public List<Material> getPlayerInventoryHighlights() {
        final Set<Material> highlightSet = new HashSet<>();

        Arrays.stream(player.getInventory().getStorageContents()).forEach(item -> {
            if (item != null && InventoryHighlights.contains(item.getType())) {
                highlightSet.add(item.getType());
            }
        });

        return highlightSet.stream().toList();
    }

    public String getId () {
        return id;
    }

    public String getPlayerName () {
        return player.getDisplayName();
    }

    public int getPlayerHealth() { return (int) Math.ceil(player.getHealth()); }

    public String getTimeString() {
        return time.toString();
    }

    public Type getType() { return type; }

    public JsonObject encodeToJson() {
        final JsonObject json = new JsonObject();
        json.addProperty("id", getId());
        json.addProperty("player", getPlayerName());
        json.addProperty("health", getPlayerHealth());
        json.addProperty("time", getTimeString());
        json.addProperty("type", type.label);
        JsonArray highlights = new JsonArray();
        getPlayerInventoryHighlights().forEach((item) -> highlights.add(item.toString()));
        json.add("highlights", highlights);
        return json;
    }
}
