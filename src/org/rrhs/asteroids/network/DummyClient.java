package org.rrhs.asteroids.network;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.util.logging.Logger;

/**
 * Client class that doesn't perform any network actions.
 * Intended for testing only.
 */
public class DummyClient extends Client
{
    public DummyClient(GameState state)
    {
        super(state);
    }

    @Override
    public void process(String s)
    {
        Logger.debug("Received message from server: " + s);
    }

    @Override
    protected void init()
    {
        Logger.debug("Dummy client initialized");
    }


    @Override
    public void send(Packet message)
    {
        send(message.toString());
    }

    @Override
    public void send(String message)
    {
        Logger.debug("Message send attempt: {" + message + "}");
    }
}
