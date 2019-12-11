package org.rrhs.asteroids.network.actions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class FireLaserAction implements NetworkAction {
    @Override
    public void act(Server server, int clientID, Map<String, String> parsedMessage) {
        Map<String, String> messageToSend = new HashMap<>();
        NetworkActor turret = server.getTurret();
        server.addLaser(turret.getX(), turret.getY(), turret.getRotation());
        messageToSend.put("action", "update");
        messageToSend.put("type", "turret");
        messageToSend.put("actor", turret.toString());
        server.send(messageToSend.toString());
    }
}
