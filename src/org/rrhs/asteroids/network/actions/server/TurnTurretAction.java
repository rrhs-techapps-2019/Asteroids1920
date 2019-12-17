package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.Server;

public class TurnTurretAction implements ServerAction
{
    @Override
    public void act(Server server, int clientID, Packet packet)
    {
        NetworkActor turret = server.getTurret();
        String direction = packet.getType();
        if ("left".equals(direction))
        {
            turret.setRotationSpeed(-1);
        } else
        {
            turret.setRotationSpeed(1);
        }
        Packet update = new Packet(PacketAction.UPDATE, turret);
        server.send(update);
    }
}
