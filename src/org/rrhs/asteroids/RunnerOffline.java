package org.rrhs.asteroids;

import mayflower.World;
import org.rrhs.asteroids.network.DummyClient;
import org.rrhs.asteroids.views.EngineerView;

/**
 * Offline standalone runner. To be used for testing only.<br>
 * This class does nothing by itself! Add a main method to your
 * view class and construct an instance of RunnerOffline with
 * the view class as the target argument.
 */
public class RunnerOffline extends Runner
{
    public RunnerOffline(final Class<? extends World> targetWorld)
    {
        super(targetWorld, false);
        GameState state = new GameState();
        super.initClient(new DummyClient(state), state);
    }
}
