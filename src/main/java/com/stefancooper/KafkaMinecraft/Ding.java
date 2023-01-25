package com.stefancooper.KafkaMinecraft;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Ding implements Listener {

    private Map<Block, Doorbell> doorbells = new HashMap<>();
    private Produce kafkaProducer;
    private JavaPlugin plugin;

    public Ding(Produce kafkaProducer, JavaPlugin plugin) {
        this.kafkaProducer = kafkaProducer;
        this.plugin = plugin;
    }

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("addDoorbell")) {
            Location location = new Location(Bukkit.getWorld("world"),
                    Double.parseDouble(args[0]),
                    Double.parseDouble(args[1]),
                    Double.parseDouble(args[2]));
            doorbells.put(location.getBlock(), new Doorbell(sender.getName(), location, false));
        }
        return true;
    }

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent e) throws InterruptedException {
        if(e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            int currentX = e.getTo().getBlockX();
            int currentY = e.getTo().getBlockY();
            int currentZ = e.getTo().getBlockZ();

            Collection<Doorbell> doorbellLocations = doorbells.values();

            for (Doorbell doorbell : doorbellLocations) {
                int getXMinRange = doorbell.getLocation().getBlockX() - 4;
                int getXMaxRange = doorbell.getLocation().getBlockX() + 4;
                int getYMinRange = doorbell.getLocation().getBlockY() - 4;
                int getYMaxRange = doorbell.getLocation().getBlockY() + 4;
                int getZMinRange = doorbell.getLocation().getBlockZ() - 4;
                int getZMaxRange = doorbell.getLocation().getBlockZ() + 4;

                if ((!doorbell.getMotionDetected()) &&
                        (currentX >= getXMinRange && currentX <= getXMaxRange) &&
                        (currentY >= getYMinRange && currentY <= getYMaxRange) &&
                        (currentZ >= getZMinRange && currentZ <= getZMaxRange)) {
                    Bukkit.getConsoleSender().sendMessage(doorbell.getID() + ": Motion Detected! Not detecting motion for 5 seconds...");
                    doorbell.setMotionDetected(true);
                    kafkaProducer.produceMessage("motion", "\uD83C\uDFC3 Motion Detected! " + e.getPlayer().getDisplayName() + " is nearby! \n" + new Date().toGMTString());

                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            doorbell.setMotionDetected(false);
                        }
                    }, calculateSeconds(10));
                }
            }
        }
    }

    @EventHandler
    public void placeDoorbell(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType().equals(Material.STONE_BUTTON)) {
            if (e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("doorbell")) {
                doorbells.put(e.getBlockPlaced(), new Doorbell(
                        e.getPlayer().getDisplayName(),
                        e.getBlockPlaced().getLocation(),
                        false));
            }
        }
    }

    @EventHandler
    public void pressDoorbell(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.STONE_BUTTON)) {
            if (doorbells.get(e.getClickedBlock()) != null) {
                kafkaProducer.produceMessage("ring", "\uD83D\uDD14 Doorbell pressed! " + e.getPlayer().getDisplayName() + " is at the door!\n" + new Date().toGMTString() + "\n" + doorbells.get(e.getClickedBlock()).getID());
            }
        }
    }

    private int calculateSeconds(int seconds) {
        return seconds * 20;
    }
}
