package org.rrhs.asteroids.views;

import mayflower.Color;
import mayflower.Keyboard;
import mayflower.MayflowerImage;
import mayflower.World;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerClient;
import org.rrhs.asteroids.RunnerServer;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.util.MayflowerUtils;
import org.rrhs.asteroids.util.logging.LogLevel;
import org.rrhs.asteroids.util.logging.LoggerConfigurator;
import org.rrhs.asteroids.util.logging.writers.ConsoleWriter;

import java.util.HashMap;
import java.util.Map;

public class GameView extends World
{
    private Client client;
    private GameState state;
    private Map<Integer, NetworkActor> actors = new HashMap<>();
    private int resyncCounter = 0;

    public GameView(Client client, GameState state)
    {
        this.client = client;
        this.state = state;
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
            Packet p = new Packet(PacketAction.UPDATE);
            client.send(p);
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
                actor = nActor;
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
            Packet p = new Packet(PacketAction.UPDATE);
            client.send(p);
        }

        // move forward
        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_UP))
        {
            Packet p = new Packet(PacketAction.MOVE);
            client.send(p);
        }
        else if (MayflowerUtils.wasKeyReleased(Keyboard.KEY_UP))
        {
            Packet p = new Packet(PacketAction.STOP);
            client.send(p);
        }

        // turn left
        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_LEFT))
        {
            Packet p = new Packet(PacketAction.TURN, "left");
            client.send(p);
        }
        else if (MayflowerUtils.wasKeyReleased(Keyboard.KEY_LEFT))
        {
            Packet p = new Packet(PacketAction.STOP_TURN);
            client.send(p);
        }

        // turn right
        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_RIGHT))
        {
            Packet p = new Packet(PacketAction.TURN, "right");
            client.send(p);
        }
        else if (MayflowerUtils.wasKeyReleased(Keyboard.KEY_RIGHT))
        {
            Packet p = new Packet(PacketAction.STOP_TURN);
            client.send(p);
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        LoggerConfigurator.empty()
                .addWriter(new ConsoleWriter())
                .level(LogLevel.DEBUG)
                .activate();
        new RunnerServer();
        new Thread(() -> {
            try
            {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            new RunnerClient(GameView.class);
        }).start();
    }
}
  
  
    
  