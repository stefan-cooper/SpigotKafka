package com.stefancooper.KafkaMinecraft;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.UUID;

public class Doorbell {

    private Location location;
    private boolean motionDetected;
    private String id;

    public Doorbell (String playerName, Location location, boolean motionDetected) {
        this.location = location;
        this.motionDetected = motionDetected;
        this.id = playerName + "-" + UUID.randomUUID().toString();
    }

    public Location getLocation() {
        return location;
    }

    public boolean getMotionDetected() {
        return motionDetected;
    }

    public String getID() {
        return id;
    }

    public void setMotionDetected(boolean motionDetected) {
        this.motionDetected = motionDetected;
    }
}
