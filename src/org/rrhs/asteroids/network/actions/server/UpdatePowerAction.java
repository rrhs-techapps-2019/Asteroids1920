package org.rrhs.asteroids.network.actions.server;

import org.rrhs.asteroids.System;
import org.rrhs.asteroids.actors.data.PowerData;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.Server;
import org.rrhs.asteroids.util.NetworkUtils;

import java.util.Map;

public class UpdatePowerAction implements ServerAction
{
    @Override
    public void act(Server server, int clientID, Packet packet)
    {
        Map<System, Integer> dataMap = NetworkUtils.reconstituteMap(packet.getData(), System::valueOf, Integer::valueOf);
        PowerData data = new PowerData(dataMap);
        server.setPowerState(data);
        server.send(packet);
    }
}
