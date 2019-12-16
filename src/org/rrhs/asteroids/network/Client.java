package org.rrhs.asteroids.network;

import org.rrhs.asteroids.GameState;
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
        Packet packet = new Packet(message);
        Logger.debug("Process: " + message);
        Logger.debug(packet);
        state.addUpdate(packet.toString());
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
        Logger.info("Connected to server!");
        connected = true;
    }

    /**
     * Initialize this Client. Called once upon instantiation.
     */
    protected void init()
    {
        connect("localhost", 8080);
    }

    /**
     * Send a Packet to the server
     */
    public void send(Packet message)
    {
        super.send(message.toString());
    }
}