package org.rrhs.asteroids.network.serverActions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class UpdateAllAction implements NetworkAction {

    public void act(Server server, int clientID, Packet packet) {
        Map<Integer, NetworkActor> actors = server.getActors();
        for (NetworkActor actor : actors.values())
        {
            Packet p = new Packet("add", actor);
            server.send(clientID, p.toString());
        }

    }
}
