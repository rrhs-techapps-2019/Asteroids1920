package org.rrhs.asteroids;

import org.rrhs.asteroids.actors.Asteroid;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.Ship;
import org.rrhs.asteroids.network.MessageHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class GameState
{
    private Queue<String> updates;
    private Map<Integer, NetworkActor> actors;
    private MessageHandler messageHandler;

    public GameState()
    {
        updates = new LinkedList<>();
        actors = new HashMap<>();
        messageHandler = new MessageHandler();
    }


    public NetworkActor[] getActors()
    {
        return actors.values().toArray(new NetworkActor[]{});
    }

    public void act()
    {
        processUpdates();
        processActors();
    }

    public void processActors()
    {
        for (NetworkActor actor : actors.values())
        {
            actor.act();
        }
    }

    private void processUpdates()
    {
        for (int i = 0; i < numUpdates(); i++) {
            String update = getNextUpdate();
            Map<String, String> parsedMessage = messageHandler.parseMessage(update);
            String action = parsedMessage.get("action");

            String type = parsedMessage.get("type");
            Map<String, String> positionData = messageHandler.parseMessage(parsedMessage.get("actor"));
            int id = Integer.parseInt(positionData.get("id"));
            int x = Integer.parseInt(positionData.get("x"));
            int y = Integer.parseInt(positionData.get("y"));
            int rot = Integer.parseInt(positionData.get("rot"));
            int speed = Integer.parseInt(positionData.get("speed"));
            int rotationSpeed = Integer.parseInt(positionData.get("rotspeed"));

            NetworkActor actor = actors.get(id);

            if (null == actor) {
                if ("ship".equals(type)) {
                    actor = new Ship(id);
                } else {
                    actor = new Asteroid(id);
                }

                actors.put(id, actor);
            }
            actor.setLocation(x, y);
            actor.setRotation(rot);
            actor.setSpeed(speed);
            actor.setRotationSpeed(rotationSpeed);
        }
    }

    public String getNextUpdate()
    {
        return updates.remove();
    }

    public void addUpdate(String update)
    {
        updates.add(update);
    }

    public int numUpdates()
    {
        return updates.size();
    }
}