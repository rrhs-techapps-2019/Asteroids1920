package org.rrhs.asteroids.views;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.network.Client;

public class Sensors extends GameView
{
    //800 by 600 screen per person
    public Sensors(Client client, GameState state)
    {
        super(client, state);
    }

    @Override
    protected void update()
    {
    }
}