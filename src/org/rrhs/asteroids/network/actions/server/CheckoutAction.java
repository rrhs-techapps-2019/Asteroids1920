package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.Role;
import org.rrhs.asteroids.actors.data.RoleData;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.Server;
import org.rrhs.asteroids.util.logging.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

public class CheckoutAction implements ServerAction {
    private static AtomicBoolean weaponsTaken = new AtomicBoolean();
    private static AtomicBoolean pilotTaken = new AtomicBoolean();
    private static AtomicBoolean sensorsTaken = new AtomicBoolean();
    private static AtomicBoolean engineerTaken = new AtomicBoolean();

    @Override
    public void act(Server server, int clientID, Packet packet) {
        int roleOrdinal = Integer.parseInt(packet.getType());
        Role role = Role.values()[roleOrdinal];
        RoleData roleState = server.getRoleState();

        if(roleState.isAvailable(role)) {
            roleState.claim(role);
        }

        server.send(new Packet(PacketAction.CHECKOUT, roleState));
    }
}
