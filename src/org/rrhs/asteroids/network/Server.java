package org.rrhs.asteroids.network;

import mayflower.World;
import org.rrhs.asteroids.actors.objects.Asteroid;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.objects.Ship;
import org.rrhs.asteroids.network.actions.NetworkAction;

import java.util.HashMap;
import java.util.Map;

public class Server extends mayflower.net.Server
{
    private World world;
    private Ship ship;
    private Map<Integer, NetworkActor> actors;
    private int nextActorId;
    private MessageHandler messageHandler;
    private Map<String, NetworkAction> actions;

    public Server(World world)
    {
        super(8080);
        actors = new HashMap<>();
        nextActorId = 1;
        this.world = world;
        ship = new Ship(0);
        actors.put(ship.getId(), ship);
        messageHandler = new MessageHandler();
        actions = new HashMap<>();
        world.addObject(ship, world.getWidth() / 2, world.getHeight() / 2);

        addAsteroid(10, 10, 10, 1);
        addAsteroid(600, 200, -100, 1);
        System.out.println("Server started...");
    }

    public void addAsteroid(int x, int y, int direction, int speed)
    {
        Asteroid asteroid = new Asteroid(nextActorId++);
        world.addObject(asteroid, x, y);
        asteroid.setRotation(direction);
        asteroid.setSpeed(speed);
        actors.put(asteroid.getId(), asteroid);
        System.out.println(asteroid);
        HashMap<String, String> messageToSend = new HashMap<>();
        messageToSend.put("action", "add");
        messageToSend.put("type", asteroid.getType());
        messageToSend.put("actor", asteroid.toString());
        send(messageToSend.toString());
    }

    /**
     * Handle a message from a client
     *
     * @param id      the id of the client that send the message
     * @param message the message send from the client
     */
    public void process(int id, String message)
    {
        Map<String, String> parsedMessage = messageHandler.parseMessage(message);

        System.out.println("Process (" + id + "): " + message);

        String[] parts = message.split(" ");
        String action = parts[0];

        String type, direction;
        Map<String, String> messageToSend = new HashMap<>();
        actions.get(parsedMessage.get("action")).act(this);
        /*
        switch (parsedMessage.get("action"))
        {
            case "update":
                //update the state of all the actors in the world
                for (NetworkActor actor : actors.values())
                {
                    messageToSend.put("action", "add");
                    messageToSend.put("type", actor.getType());
                    messageToSend.put("actor", actor.toString());
                    send(id, messageToSend.toString());
                }
                break;
            case "move":
                //make ship start moving
                ship.setSpeed(1);
                messageToSend.put("action", "update");
                messageToSend.put("type", "ship");
                messageToSend.put("actor", ship.toString());
                send(messageToSend.toString());
                break;
            case "turn":
                //make ship start turning
                direction = parsedMessage.get("direction");
                if ("left".equals(direction))
                {
                    ship.setRotationSpeed(-1);
                } else
                {
                    ship.setRotationSpeed(1);
                }
                messageToSend.put("action", "update");
                messageToSend.put("type", "ship");
                messageToSend.put("actor", ship.toString());
                send(messageToSend.toString());
                break;
            case "stop":
                type = parts[1];

                if ("turn".equals(type))
                {
                    //make ship stop turning
                    ship.setRotationSpeed(0);
                } else
                {
                    //make ship stop moving
                    ship.setSpeed(0);
                }
                messageToSend.put("action", "update");
                messageToSend.put("type", "ship");
                messageToSend.put("actor", ship.toString());
                send(messageToSend.toString());
                break;
        }
        */
    }

    /**
     * Handle a new connection to the server
     *
     * @param id the id of the client that just connected to the server
     */
    public void onJoin(int id)
    {
        System.out.println("Joined: " + id);

        for (NetworkActor actor : actors.values())
        {
            send(id, "add " + actor.getType() + " " + actor);
        }
    }

    public boolean addNetworkAction(String type, NetworkAction action) {
        actions.put(type, action);
        return true;
    }

    /**
     * Handle a client that disconnected from the server
     *
     * @param id the id of the client that disconnected
     */
    public void onExit(int id)
    {
        System.out.println("Disconnected: " + id);
    }
}