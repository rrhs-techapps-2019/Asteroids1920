package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.Role;
import org.rrhs.asteroids.actors.data.RoleData;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.Server;

public class ClaimRoleAction implements ServerAction
{
    @Override
    public void act(Server server, int clientID, Packet packet)
    {
        int roleOrdinal = Integer.parseInt(packet.getType());
        Role role = Role.values()[roleOrdinal];
        RoleData roleState = server.getRoleState();

        if (roleState.isAvailable(role))
        {
            roleState.claim(role, clientID);
        }

        server.send(new Packet(PacketAction.UPDATE_ROLES, roleState));
    }
}
