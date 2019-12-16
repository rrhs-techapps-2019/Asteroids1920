package org.rrhs.asteroids.network.actions.client;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.network.Packet;

public interface ClientAction
{
    void act(GameState state, Packet packet);
}