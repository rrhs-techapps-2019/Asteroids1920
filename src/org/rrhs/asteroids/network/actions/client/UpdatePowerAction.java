package org.rrhs.asteroids.network.actions.client;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.System;
import org.rrhs.asteroids.actors.data.PowerData;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.util.NetworkUtils;

import java.util.Map;

public class UpdatePowerAction implements ClientAction
{
    @Override
    public void act(GameState state, Packet packet)
    {
        Map<System, Integer> dataMap = NetworkUtils.reconstituteMap(packet.getData(), System::valueOf, Integer::valueOf);
        PowerData data = new PowerData(dataMap);
        state.setPowerState(data);
    }
}
