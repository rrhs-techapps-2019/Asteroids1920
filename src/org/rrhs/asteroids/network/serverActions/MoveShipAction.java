package org.rrhs.asteroids.network.serverActions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.Server;

public class MoveShipAction implements ServerAction {

    @Override
    public void act(Server server, int clientID, Packet packet) {
        NetworkActor ship = server.getShip();
        NetworkActor turret = server.getTurret();
        ship.setSpeed(1);
        turret.setLocation(ship.getX(), ship.getY());
        Packet p = new Packet("update", ship);
        Packet turrP = new Packet("update", turret);
        server.send(p.toString());
        server.send(turrP.toString());
    }
}
