package org.rrhs.asteroids.network.actions.client;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.network.Packet;

public class IdentAction implements ClientAction
{
    @Override
    public void act(GameState state, Packet packet)
    {
        int id = Integer.parseInt(packet.getType());
        state.setClientId(id);
    }
}
