package org.rrhs.asteroids.network.actions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class UpdateAllAction implements NetworkAction {
    @Override
    public void act(Server server) {
        Map<String, String> messageToSend = new HashMap<>();

        for (NetworkActor actor : actors.values())
        {
            messageToSend.put("action", "add");
            messageToSend.put("type", actor.getType());
            messageToSend.put("actor", actor.toString());
            server.send(id, messageToSend.toString());
        }

    }
}
