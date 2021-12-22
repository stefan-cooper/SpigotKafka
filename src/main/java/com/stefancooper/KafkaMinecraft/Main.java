package com.stefancooper.KafkaMinecraft;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin implements Listener {

    private ArrayList<Doorbell> doorbells = new ArrayList<>(Arrays.asList());
    private Produce kafkaProducer;

    public void onEnable() { // This is called when the plugin is loaded into the server.

        kafkaProducer = new Produce();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() { // This is called when the plugin is unloaded from the server.

    }

    @EventHandler
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("addDoorbell")) {
            doorbells.add(new Doorbell(
                    sender.getName(),
                    new Location(Bukkit.getWorld("world"),
                            Double.parseDouble(args[0]),
                            Double.parseDouble(args[1]),
                            Double.parseDouble(args[2])
                    ), false));
        }
        return true;
    }

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent e) throws InterruptedException {
        if(e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            int currentX = e.getTo().getBlockX();
            int currentY = e.getTo().getBlockY();
            int currentZ = e.getTo().getBlockZ();

            for (Doorbell doorbell : doorbells) {
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

                    Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
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
    public void notifyLogin(PlayerLoginEvent e) {
        kafkaProducer.produceMessage("login", "\uD83D\uDD17 Player connected: " + e.getPlayer().getDisplayName() + "\n" + new Date().toGMTString());
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

    private int calculateSeconds(int seconds) {
        return seconds * 20;
    }
}
