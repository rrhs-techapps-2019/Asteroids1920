package org.rrhs.asteroids.network.actions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class MoveShipAction implements NetworkAction {

    @Override
    public void act(Server server, int clientID, Map<String, String> parsedMessage) {
        NetworkActor ship = server.getShip();
        Map<String, String> messageToSend = new HashMap<>();
        ship.setSpeed(1);
        messageToSend.put("action", "update");
        messageToSend.put("type", "ship");
        messageToSend.put("actor", ship.toString());
        server.send(messageToSend.toString());

    }
}
