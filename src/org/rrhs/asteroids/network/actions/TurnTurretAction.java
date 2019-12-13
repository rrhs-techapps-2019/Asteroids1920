package org.rrhs.asteroids.network.actions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class TurnTurretAction implements ServerAction {
    @Override
    public void act(Server server, int clientID, Packet packet) {
        NetworkActor turret = server.getTurret();
        String direction = packet.getData();
        if ("left".equals(direction))
        {
            turret.setRotationSpeed(-1);
        } else
        {
            turret.setRotationSpeed(1);
        }
        Packet p = new Packet("update", turret);
        server.send(p.toString());

    }
}
