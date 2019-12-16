package org.rrhs.asteroids.network.actions.client;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.util.NetworkUtils;

import java.util.Map;

public class AddActorAction implements ClientAction
{
    @Override
    public void act(GameState state, Packet packet)
    {
        Map<String, Integer> data = NetworkUtils.reconstituteMap(packet.getData());
        int id = data.get("id");
        NetworkActor actor = NetworkUtils.reconstituteActor(packet.getType(), data);
        state.addActor(id, actor);
    }
}