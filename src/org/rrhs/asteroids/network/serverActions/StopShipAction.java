package org.rrhs.asteroids.network.serverActions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class StopShipAction implements NetworkAction {
    @Override
    public void act(Server server, int clientID, Map<String, String> parsedMessage) {
        String action = parsedMessage.get("action");
        NetworkActor ship = server.getShip();
        Map<String, String> messageToSend = new HashMap<>();

        if ("turn".equals(action))
        {
            //make ship stop turning
            ship.setRotationSpeed(0);
        } else
        {
            //make ship stop moving
            ship.setSpeed(0);
        }
        messageToSend.put("action", "update");
        messageToSend.put("type", "ship");
        messageToSend.put("actor", ship.toString());
        server.send(messageToSend.toString());
    }
}
