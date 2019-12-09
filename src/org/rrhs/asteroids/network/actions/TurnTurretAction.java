package org.rrhs.asteroids.network.actions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class TurnTurretAction implements NetworkAction {
    @Override
    public void act(Server server, int clientID, Map<String, String> parsedMessage) {
        Map<String, String> messageToSend = new HashMap<>();
        NetworkActor turret = server.getShip();
        String direction = parsedMessage.get("direction");
        if ("left".equals(direction))
        {
            turret.setRotationSpeed(-1);
        } else
        {
            turret.setRotationSpeed(1);
        }
        messageToSend.put("action", "update");
        messageToSend.put("type", "ship");
        messageToSend.put("actor", turret.toString());
        server.send(messageToSend.toString());

    }
}
