package org.rrhs.asteroids.views;

import mayflower.Color;
import mayflower.MayflowerImage;
import mayflower.World;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;

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
     *     <li>{@link #update}</li>
     * </ol>
     * You don't need to override this; override {@link #update} instead.
     */
    @Override
    public void act()
    {
        resync();
        state.act();
        update();
    }

    /**
     * Performs necessary per-frame view updates. Called every frame after sync.
     */
    protected abstract void update();

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
  
  
    
  