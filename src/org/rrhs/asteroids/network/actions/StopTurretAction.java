package org.rrhs.asteroids.network.actions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class StopTurretAction implements NetworkAction {
    @Override
    public void act(Server server, int clientID, Map<String, String> parsedMessage) {
        String action = parsedMessage.get("action");
        NetworkActor turret = server.getTurret();
        Map<String, String> messageToSend = new HashMap<>();

        if ("turn".equals(action))
        {
            //make ship stop turning
            turret.setRotationSpeed(0);
        } else
        {
            //make ship stop moving
            turret.setSpeed(0);
        }
        messageToSend.put("action", "update");
        messageToSend.put("type", "ship");
        messageToSend.put("actor", turret.toString());
        server.send(messageToSend.toString());
    }
}
