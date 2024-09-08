package com.stefancooper.SpigotKafka;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Plugin extends JavaPlugin implements Listener {

    private Produce kafkaProducer;

    public void onEnable() { // This is called when the plugin is loaded into the server.
        kafkaProducer = new Produce();
        Bukkit.getPluginManager().registerEvents(new Core(kafkaProducer), this);
    }

    public void onDisable() { // This is called when the plugin is unloaded from the server.

    }
}
