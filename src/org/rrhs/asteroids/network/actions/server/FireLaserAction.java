package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.Server;

public class FireLaserAction implements ServerAction
{
    @Override
    public void act(Server server, int clientID, Packet packet)
    {
        NetworkActor turret = server.getTurret();
        server.addLaser(turret.getX(), turret.getY(), turret.getRotation());
        Packet update = new Packet(PacketAction.UPDATE, turret);
        server.send(update);
    }
}
