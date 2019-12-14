package org.rrhs.asteroids;

import org.rrhs.asteroids.actors.objects.Asteroid;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.objects.Ship;
import org.rrhs.asteroids.network.MessageHandler;
import org.rrhs.asteroids.network.Packet;

import java.util.*;

public class GameState
{
    private Queue<String> updates;
    private Map<Integer, NetworkActor> actors;
    MessageHandler messageParser = new MessageHandler();

    public GameState()
    {
        updates = new LinkedList<>();
        actors = new HashMap<>();
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
        for (int i = 0; i < numUpdates(); i++)
        {
            Packet update = messageParser.parseMessage(getNextUpdate());
            System.out.println(update.getData());
            String[] parts = update.getData().split(" ");
            if (parts[0] != null) continue;
            if (parts[0].equals("disconnect")) continue;
            System.out.println(Arrays.toString(parts));
            String action = parts[0];

            String type = parts[2];
            int id = Integer.parseInt(parts[2]);
            int x = Integer.parseInt(parts[3]);
            int y = Integer.parseInt(parts[4]);
            int rot = Integer.parseInt(parts[5]);
            int speed = Integer.parseInt(parts[6]);
            int rotationSpeed = Integer.parseInt(parts[7]);

            NetworkActor actor = actors.get(id);

            if (null == actor)
            {
                if ("ship".equals(type))
                {
                    actor = new Ship(id);
                } else
                {
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
        System.out.println("update>>" + update);
        updates.add(update);
    }

    public int numUpdates()
    {
        return updates.size();
    }
}