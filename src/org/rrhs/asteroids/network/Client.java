package org.rrhs.asteroids.network;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.util.NetworkUtils;
import org.rrhs.asteroids.util.logging.Logger;

public class Client extends mayflower.net.Client
{
    private boolean connected = false;
    private GameState state;

    public Client(GameState state)
    {
        this.state = state;
        init();
    }

    public boolean isConnected()
    {
        return connected;
    }

    /**
     * Handle a message received from the server
     *
     * @param message the message received from the server
     */
    public void process(String message)
    {
        System.out.println("Process: " + message);
        state.addUpdate(message);
    }

    /**
     * Handle being disconnected by the server
     *
     * @param message the message send by the server explaining why you were disconnected
     */
    public void onDisconnect(String message)
    {
        Logger.debug("Disconnected from server: " + message);
    }

    /**
     * Handle being connected to the server.
     */
    public void onConnect()
    {
        System.out.println("Connected to server!");
        connected = true;
    }

    /**
     * Send a predefined message to the server
     *
     * @param message Message to send
     * @see org.rrhs.asteroids.util.NetworkUtils.Message
     */
    public void send(NetworkUtils.Message message)
    {
        super.send(message.getRaw());
    }

    /**
     * Initialize this Client. Called once upon instantiation.
     */
    protected void init()
    {
        connect("localhost", 8080);
    }
}