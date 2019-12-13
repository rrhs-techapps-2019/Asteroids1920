package org.rrhs.asteroids.network;

import mayflower.World;
import org.rrhs.asteroids.actors.objects.Asteroid;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.objects.Ship;
import org.rrhs.asteroids.network.serverActions.MoveShipAction;
import org.rrhs.asteroids.network.serverActions.ServerAction;

import java.util.HashMap;
import java.util.Map;

public class Server extends mayflower.net.Server {
    private World world;
    private Ship ship;
    private Map<Integer, NetworkActor> actors;
    private int nextActorId;
    private MessageHandler messageHandler;
    private Map<String, ServerAction> actions;

    public Server(World world) {
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

        // instantiate your NetworkActions here! //
        addNetworkAction("moveship", MoveShipAction.class);
        //    end NetworkAction instantiation    //
    }

    public void addAsteroid(int x, int y, int direction, int speed) {
        Asteroid asteroid = new Asteroid(nextActorId++);
        world.addObject(asteroid, x, y);
        asteroid.setRotation(direction);
        asteroid.setSpeed(speed);
        actors.put(asteroid.getId(), asteroid);
        System.out.println(asteroid);
        Packet p = new Packet("add", asteroid);
        send(p.toString());
    }

    /**
     * Handle a message from a client
     *
     * @param id      the id of the client that send the message
     * @param message the message send from the client
     */
    public void process(int id, String message) {
        Packet packet = messageHandler.parseMessage(message);

        System.out.println("Process (" + id + "): " + message);

        String[] parts = message.split(" ");
        String action = packet.getAction();

        String type, direction;
        if (actions.containsKey(packet.type))
            actions.get(packet.type).act(this, id, packet);
    }

    /**
     * Handle a new connection to the server
     *
     * @param id the id of the client that just connected to the server
     */
    public void onJoin(int id) {
        System.out.println("Joined: " + id);

        for (NetworkActor actor : actors.values()) {
            Packet p = new Packet("add", actor);
            System.out.println(">>" + p.toString());
            send(id, p.toString());
        }
    }

    /**
     * @param action        the action occurring i.e. "update"
     * @param networkAction the networkAction that will have act() called
     * @return true, eventually the status of the action.
     */
    public boolean addNetworkAction(String action, Class<? extends ServerAction> networkAction) {
        ServerAction actionInstance = null;
        try {
            actionInstance = networkAction.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        actions.put(action, actionInstance);
        return true;
    }

    /**
     * Handle a client that disconnected from the server
     *
     * @param id the id of the client that disconnected
     */
    public void onExit(int id) {
        System.out.println("Disconnected: " + id);
    }

    public Map<Integer, NetworkActor> getActors() {
        return actors;
    }

    public NetworkActor getShip() {
        return ship;
    }
}