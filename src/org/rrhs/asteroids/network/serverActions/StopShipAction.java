package org.rrhs.asteroids.network.serverActions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.Server;

public class StopShipAction implements ServerAction
{
    @Override
    public void act(Server server, int clientID, Packet packet)
    {
        String action = packet.getAction();
        NetworkActor ship = server.getShip();
        Packet p = new Packet("update", ship);
        if ("turn".equals(action))
        {
            //make ship stop turning
            ship.setRotationSpeed(0);
        }
        else
        {
            //make ship stop moving
            ship.setSpeed(0);
        }
        server.send(p.toString());
    }
}
