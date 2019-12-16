package org.rrhs.asteroids;

import mayflower.World;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.views.GameView;

public class RunnerClient extends Runner
{
    public RunnerClient(final Class<? extends World> targetWorld)
    {
        super(targetWorld, false);
        GameState state = new GameState();
        super.initClient(new Client(state), state);
    }

    public static void main(String[] args)
    {
        // Lobby is in the base package for some reason, so I figure we'll just wait for merge
        new RunnerClient(GameView.class);
    }
}