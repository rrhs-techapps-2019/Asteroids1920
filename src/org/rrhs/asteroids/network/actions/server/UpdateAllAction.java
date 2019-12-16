package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.Server;

import java.util.Map;

public class UpdateAllAction implements ServerAction
{
    public void act(Server server, int clientID, Packet packet)
    {
        Map<Integer, NetworkActor> actors = server.getActors();
        for (NetworkActor actor : actors.values())
        {
            Packet update = new Packet(PacketAction.ADD, actor);
            server.send(clientID, update);
        }
    }
}