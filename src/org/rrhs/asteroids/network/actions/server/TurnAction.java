package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.Server;

public class TurnAction implements ServerAction
{
    @Override
    public void act(Server server, int clientID, Packet packet)
    {
        NetworkActor ship = server.getShip();
        String direction = packet.getType();
        if ("left".equals(direction))
        {
            ship.setRotationSpeed(-1);
        }
        else
        {
            ship.setRotationSpeed(1);
        }
        Packet update = new Packet(PacketAction.UPDATE, ship);
        server.send(update);
    }
}
