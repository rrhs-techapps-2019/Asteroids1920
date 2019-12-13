package org.rrhs.asteroids.network;

import mayflower.World;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.data.PowerData;
import org.rrhs.asteroids.actors.objects.Asteroid;
import org.rrhs.asteroids.actors.objects.Ship;
import org.rrhs.asteroids.network.actions.server.*;
import org.rrhs.asteroids.util.logging.Logger;
import org.rrhs.asteroids.views.EngineerView;

import java.util.HashMap;
import java.util.Map;

public class Server extends mayflower.net.Server
{
    private World world;
    private Ship ship = new Ship(0);
    private PowerData powerState = EngineerView.getDefaultPowerState();
    private ActionManager<ServerAction> actionManager = new ActionManager<>();
    private Map<Integer, NetworkActor> actors = new HashMap<>(); // ID -> actor map
    private int nextActorId = 1;                                 // Next new actor ID in sequence

    public Server(World world)
    {
        super(8080);
        this.world = world;

        // Drop the ship into the center of the world
        actors.put(ship.getId(), ship);
        world.addObject(ship, world.getWidth() / 2, world.getHeight() / 2);

        // Add initial asteroids
        addAsteroid(10, 10, 10, 1);
        addAsteroid(600, 200, -100, 1);

        // instantiate your NetworkActions here! //
        actionManager.put(PacketAction.UPDATE, UpdateAllAction.class);
        actionManager.put(PacketAction.MOVE, MoveAction.class);
        actionManager.put(PacketAction.STOP, StopMoveAction.class);
        actionManager.put(PacketAction.TURN, TurnAction.class);
        actionManager.put(PacketAction.STOP_TURN, StopTurnAction.class);
        actionManager.put(PacketAction.POWER, UpdatePowerAction.class);
        //    end NetworkAction instantiation    //

        Logger.info("Server started.");
    }

    public void addAsteroid(int x, int y, int direction, int speed)
    {
        // Add asteroid to world
        Asteroid asteroid = new Asteroid(nextActorId++);
        world.addObject(asteroid, x, y);
        asteroid.setRotation(direction);
        asteroid.setSpeed(speed);

        // Save ID and send to clients
        actors.put(asteroid.getId(), asteroid);
        Logger.debug(asteroid);
        Packet p = new Packet(PacketAction.ADD, asteroid);
        send(p);
    }

    public void addLaser(int x, int y, int direction)
    {
        Laser laser = new Laser(nextActorId++);
        world.addObject(laser, x ,y);
        laser.setRotation(direction);
        laser.setSpeed(5);
        actors.put(laser.getId(), laser);
        System.out.println(laser);
        HashMap<String, String> messageToSend = new HashMap<>();
        messageToSend.put("action", "add");
        messageToSend.put("type", laser.getType());
        messageToSend.put("actor", laser.toString());
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
        Packet packet = new Packet(message);
        Logger.debug("Process (" + id + "): " + message);
        ServerAction action = actionManager.get(packet.getAction());
        action.act(this, id, packet);
    }

    /**
     * Handle a new connection to the server
     *
     * @param id the id of the client that just connected to the server
     */
    public void onJoin(int id)
    {
        Logger.info("Client joined: ID " + id);

        Packet powerUpdate = new Packet(PacketAction.POWER, powerState);
        send(id, powerUpdate);

        for (NetworkActor actor : actors.values())
        {
            Packet actorUpdate = new Packet(PacketAction.ADD, actor);
            Logger.debug("Sending actor " + actor.getId() + " -- " + actorUpdate.toString());
            send(id, actorUpdate);
        }
    }

    /**
     * Handle a client that disconnected from the server
     *
     * @param id the id of the client that disconnected
     */
    public void onExit(int id)
    {
        Logger.info("Client disconnected: ID " + id);
    }

    public Map<Integer, NetworkActor> getActors()
    {
        return actors;
    }

    public NetworkActor getShip()
    {
        return ship;
    }

    public void setPowerState(PowerData powerState)
    {
        this.powerState = powerState;
    }

    public void send(Packet message)
    {
        super.send(message.toString());
    }

    public void send(int id, Packet message)
    {
        super.send(id, message.toString());
    }
}