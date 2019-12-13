package org.rrhs.asteroids.network.clientActions;

import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.Server;

public interface ClientAction {
    public void act(Server server, int clientID, Packet packet);

}
