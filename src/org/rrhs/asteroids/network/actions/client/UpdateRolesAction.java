package org.rrhs.asteroids.network.actions.client;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.Role;
import org.rrhs.asteroids.actors.data.RoleData;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.util.NetworkUtils;

import java.util.Map;

public class UpdateRolesAction implements ClientAction
{
    @Override
    public void act(GameState state, Packet packet)
    {
        Map<Role, Integer> data = NetworkUtils.reconstituteMap(packet.getData(), Role::valueOf, Integer::parseInt);
        state.setRoleState(new RoleData(data));
    }
}