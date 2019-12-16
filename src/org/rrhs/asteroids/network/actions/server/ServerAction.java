package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.Server;

public interface ServerAction
{
    void act(Server server, int clientID, Packet packet);
}