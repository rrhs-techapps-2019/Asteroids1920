package org.rrhs.asteroids.views;

import mayflower.Color;
import mayflower.Keyboard;
import mayflower.MayflowerImage;
import mayflower.World;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.actors.objects.Asteroid;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.objects.Ship;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.util.MayflowerUtils;
import org.rrhs.asteroids.util.NetworkUtils.Message;

import java.util.HashMap;
import java.util.Map;

public class GameView extends World
{
    private Client client;
    private GameState state;
    private Map<Integer, NetworkActor> actors;
    private int resyncCounter;

    public GameView(Client client, GameState state)
    {
        this.client = client;
        this.state = state;
        resyncCounter = 0;

        actors = new HashMap<>();

        setBackground(new MayflowerImage(getWidth(), getHeight(), Color.BLACK));
    }


    public void act()
    {
        resync();
        state.act();
        processInput();
        processActors();
    }

    public void resync()
    {
        resyncCounter++;
        if (resyncCounter > 30)
        {
            client.send(Message.UPDATE);
            resyncCounter = 0;
        }
    }

    public void processActors()
    {
        for (NetworkActor nActor : state.getActors())
        {
            int id = nActor.getId();
            NetworkActor actor = actors.get(id);
            if (null == actor)
            {
                if ("ship".equals(nActor.getType()))
                {
                    actor = new Ship(id);
                }
                else
                {
                    actor = new Asteroid(id);
                }
                addObject(actor, nActor.getX(), nActor.getY());
                actors.put(id, actor);
            }

            actor.setLocation(nActor.getX(), nActor.getY());
            actor.setRotation(nActor.getRotation());
            actor.setSpeed(nActor.getSpeed());
            actor.setRotationSpeed(nActor.getRotationSpeed());
        }
    }

    private void processInput()
    {
        // force server update
        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_SPACE))
        {
            client.send(Message.UPDATE);
        }

        // move forward
        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_UP))
        {
            client.send(Message.START_MOVE);
        }
        else if (MayflowerUtils.wasKeyReleased(Keyboard.KEY_UP))
        {
            client.send(Message.STOP_MOVE);
        }

        // turn left
        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_LEFT))
        {
            client.send(Message.START_TURN_LEFT);
        }
        else if (MayflowerUtils.wasKeyReleased(Keyboard.KEY_LEFT))
        {
            client.send(Message.STOP_TURN);
        }

        // turn right
        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_RIGHT))
        {
            client.send(Message.START_TURN_RIGHT);
        }
        else if (MayflowerUtils.wasKeyReleased(Keyboard.KEY_RIGHT))
        {
            client.send(Message.STOP_TURN);
        }
    }
}
  
  
    
  