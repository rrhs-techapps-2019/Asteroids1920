package org.rrhs.asteroids;

import mayflower.Mayflower;
import mayflower.World;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.network.DummyClient;
import org.rrhs.asteroids.util.logging.Logger;

/**
 * Offline standalone runner. To be used for testing only.<br>
 * This class does nothing by itself! Add a main method to your
 * view class and construct an instance of RunnerOffline with
 * the view class as the target argument.
 */
public class RunnerOffline extends Mayflower
{
    public RunnerOffline(final Class<? extends World> targetView)
    {
        super("Asteroids EngineerTest", 800, 600);
        try
        {
            GameState state = new GameState();
            World world = targetView.getConstructor(Client.class, GameState.class).newInstance(new DummyClient(state), state);
            Mayflower.setWorld(world);
        }
        catch (Exception e)
        {
            Logger.error(e + ": Failed to instantiate instance of target view");
        }
    }

    @Override
    public void init()
    {
        Logger.info("Mayflower initialized.");
    }
}
