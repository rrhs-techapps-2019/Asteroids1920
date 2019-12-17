package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.Server;

import java.util.HashMap;
import java.util.Map;

public class StopTurretAction implements ServerAction {
    @Override
    public void act(Server server, int clientID, Packet packet)
    {
        NetworkActor turret = server.getTurret();
        turret.setRotationSpeed(0);
        Packet update = new Packet(PacketAction.UPDATE, turret);
        server.send(update);
    }
}
