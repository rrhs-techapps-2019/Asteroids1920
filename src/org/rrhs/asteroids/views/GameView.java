package org.rrhs.asteroids.views;

import mayflower.Color;
import mayflower.MayflowerImage;
import mayflower.World;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerClient;
import org.rrhs.asteroids.RunnerServer;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.objects.Asteroid;
import org.rrhs.asteroids.actors.objects.Ship;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.util.MayflowerUtils;
import org.rrhs.asteroids.util.logging.LogLevel;
import org.rrhs.asteroids.util.logging.LoggerConfigurator;
import org.rrhs.asteroids.util.logging.writers.ConsoleWriter;

import java.util.HashMap;
import java.util.Map;

public abstract class GameView extends World
{
    protected final Client client;
    protected final GameState state;
    protected final Map<Integer, NetworkActor> actors = new HashMap<>();  // ID -> Actor map

    private int resyncCounter = 0;  // Number of frames since last forced sync

    public GameView(Client client, GameState state)
    {
        this.client = client;
        this.state = state;
        setBackground(new MayflowerImage(getWidth(), getHeight(), Color.BLACK));
    }

    /**
     * Called every frame.<br>
     * Subsequent calls, in order:
     * <ol>
     *     <li>{@link #resync}</li>
     *     <li>{@link GameState#act}</li>
     *     <li>{@link #processInput}</li>
     *     <li>{@link #draw}</li>
     * </ol>
     * You don't need to override this; override {@link #processInput} and {@link #draw} instead.
     */
    @Override
    public void act()
    {
        resync();
        state.act();
        processInput();
        draw();
    }

    /**
     * Processes keyboard input. Called every frame.
     */
    protected abstract void processInput();

    /**
     * Performs actor updates and/or draw everything on screen. Called every frame.
     */
    protected abstract void draw();

    /**
     * Forces a network update every 30 frames.
     */
    private void resync()
    {
        resyncCounter++;
        if (resyncCounter > 30)
        {
            Packet p = new Packet(PacketAction.UPDATE);
            client.send(p);
            resyncCounter = 0;
        }
    }

    // Original processActors method, for future reference
    private void processActors()
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
}
  
  
    
  