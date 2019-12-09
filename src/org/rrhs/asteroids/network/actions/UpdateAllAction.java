package org.rrhs.asteroids.network.actions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Server;

public class UpdateAllAction implements NetworkAction {
    @Override
    public void act(Server server) {

        for (NetworkActor actor : actors.values())
        {
            messageToSend.put("action", "add");
            messageToSend.put("type", actor.getType());
            messageToSend.put("actor", actor.toString());
            send(id, messageToSend.toString());
        }
        break;

    }
}
