package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.Server;

public class MoveAction implements ServerAction
{

    @Override
    public void act(Server server, int clientID, Packet packet)
    {
        NetworkActor ship = server.getShip();
        NetworkActor turret = server.getTurret();
        ship.setSpeed(1);
        turret.setLocation(ship.getX(), ship.getY());
        Packet updateShip = new Packet(PacketAction.UPDATE, ship);
        Packet updateTurret = new Packet(PacketAction.UPDATE, turret);
        server.send(updateShip);
        server.send(updateTurret);
    }
}
