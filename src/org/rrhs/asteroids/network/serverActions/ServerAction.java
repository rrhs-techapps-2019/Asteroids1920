package org.rrhs.asteroids.network.serverActions;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.Server;

import java.util.Map;

public interface NetworkAction {
    public void act(Server server, int clientID, Packet packet);
}
