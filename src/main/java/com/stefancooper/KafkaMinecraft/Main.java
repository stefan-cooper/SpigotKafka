package com.stefancooper.KafkaMinecraft;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.Ageable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;

public class Main extends JavaPlugin implements Listener {

    private ArrayList<Location> doorbells = new ArrayList<>(Arrays.asList());
    private boolean motionDetected = false;

    public void onEnable() { // This is called when the plugin is loaded into the server.

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() { // This is called when the plugin is unloaded from the server.

    }

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("addDoorbell")) {
            doorbells.add(new Location(Bukkit.getWorld("world"), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
        }
        return true;
    }

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent e) throws InterruptedException {
//        Bukkit.getConsoleSender().sendMessage(Integer.toString(e.getTo().getBlockX()));
//        Bukkit.getConsoleSender().sendMessage(Integer.toString(e.getTo().getBlockY()));
//        if (e.getTo().getBlockX() == 0 && e.getTo().getBlockZ() == 0) {
//            Bukkit.getConsoleSender().sendMessage("On 0,0");
//        }
        int currentX = e.getTo().getBlockX();
        int currentY = e.getTo().getBlockY();
        int currentZ = e.getTo().getBlockZ();
        for (Location loc : doorbells) {
            int getXMinRange = loc.getBlockX() - 4;
            int getXMaxRange = loc.getBlockX() + 4;
            int getYMinRange = loc.getBlockY() - 4;
            int getYMaxRange = loc.getBlockY() + 4;
            int getZMinRange = loc.getBlockZ() - 4;
            int getZMaxRange = loc.getBlockZ() + 4;

            if ((!motionDetected) &&
                (currentX >= getXMinRange && currentX <= getXMaxRange) &&
                (currentY >= getYMinRange && currentY <= getYMaxRange) &&
                (currentZ >= getZMinRange && currentZ <= getZMaxRange)) {
                Bukkit.getConsoleSender().sendMessage("Motion Detected!");
                motionDetected = true;
                Bukkit.getConsoleSender().sendMessage("Not detecting motion for 5 seconds...");

                Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    @Override
                    public void run() {
                        motionDetected = false;
                    }
                }, 5 * 20);
            }
        }
    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}
