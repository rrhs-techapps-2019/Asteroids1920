package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.Server;

public class StopTurnAction implements ServerAction
{
    @Override
    public void act(Server server, int clientID, Packet packet)
    {
        NetworkActor ship = server.getShip();
        ship.setRotationSpeed(0);
        Packet update = new Packet(PacketAction.UPDATE, ship);
        server.send(update);
    }
}
