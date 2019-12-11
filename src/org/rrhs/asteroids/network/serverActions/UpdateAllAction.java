package org.rrhs.asteroids.network.serverActions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class UpdateAllAction implements NetworkAction {

    @Override
    public void act(Server server, int clientID, Map<String, String> parsedMessage) {
        Map<String, String> messageToSend = new HashMap<>();
        Map<Integer, NetworkActor> actors = server.getActors();
        for (NetworkActor actor : actors.values())
        {
            messageToSend.put("action", "add");
            messageToSend.put("type", actor.getType());
            messageToSend.put("actor", actor.toString());
            server.send(clientID, messageToSend.toString());
        }

    }
}
