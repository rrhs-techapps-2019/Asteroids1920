package org.rrhs.asteroids.network.actions;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class StopTurretAction implements ServerAction {
    @Override
    public void act(Server server, int clientID, Packet packet) {
        String action = packet.getAction();
        NetworkActor turret = server.getTurret();
        Packet p = new Packet("update", turret);
        if ("turnTurret".equals(action))
        {
            //make ship stop turning
            turret.setRotationSpeed(0);
        } else
        {
            //make ship stop moving
            turret.setSpeed(0);
        }
        server.send(p.toString());
    }
}
