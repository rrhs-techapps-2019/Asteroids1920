package org.rrhs.asteroids.network;

import org.rrhs.asteroids.GameState;

import java.util.Map;

public class Client extends mayflower.net.Client
{
    private boolean connected;
    private GameState state;
    private MessageHandler messageHandler;

    public Client(GameState state)
    {
        connected = false;
        this.state = state;
        messageHandler = new MessageHandler();
        connect("localhost", 8080);
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
        Map<String, String> parsedMessage = messageHandler.parseMessage(message);
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
        System.out.println("Disconnected from server: " + message);
    }

    /**
     * Handle being connected to the server.
     */
    public void onConnect()
    {
        System.out.println("Connected to server!");
        connected = true;
    }
}