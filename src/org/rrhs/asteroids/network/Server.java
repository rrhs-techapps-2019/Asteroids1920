package org.rrhs.asteroids.network;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.Asteroid;
import org.rrhs.asteroids.actors.Ship;
import mayflower.*;

import java.util.*;

public class Server extends mayflower.net.Server
{
    private World world;
    private Ship ship;
    private Map<Integer, NetworkActor> actors;
    private int nextActorId;

    public Server(World world)
    {
        super(8080);
        actors = new HashMap<>();
        nextActorId = 1;
        this.world = world;
        ship = new Ship(0);
        actors.put(ship.getId(), ship);
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
        send("add asteroid " + asteroid);
    }

    /**
     * Handle a message from a client
     *
     * @param id      the id of the client that send the message
     * @param message the message send from the client
     */
    public void process(int id, String message)
    {
        System.out.println("Process (" + id + "): " + message);

        String[] parts = message.split(" ");
        String action = parts[0];

        String type, direction;
        switch (action)
        {
            case "update":
                //update the state of all the actors in the world
                for (NetworkActor actor : actors.values())
                {
                    send(id, "add " + actor.getType() + " " + actor);
                }
                break;
            case "move":
                //make ship start moving
                ship.setSpeed(1);
                send("update ship " + ship);
                break;
            case "turn":
                //make ship start turning
                direction = parts[1];
                if ("left".equals(direction))
                {
                    ship.setRotationSpeed(-1);
                } else
                {
                    ship.setRotationSpeed(1);
                }


                send("update ship " + ship);
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

                send("update ship " + ship);
                break;
        }
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