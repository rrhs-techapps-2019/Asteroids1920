package org.rrhs.asteroids;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.data.PowerData;
import org.rrhs.asteroids.network.ActionManager;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.actions.client.AddActorAction;
import org.rrhs.asteroids.network.actions.client.ClientAction;
import org.rrhs.asteroids.network.actions.client.UpdateActorAction;
import org.rrhs.asteroids.network.actions.client.UpdatePowerAction;

import java.util.*;

/**
 * Manages updates and actor IDs.
 */
public class GameState
{
    private final ActionManager<ClientAction> actionManager = new ActionManager<>();
    private final Queue<String> updates = new LinkedList<>();           // Updates queued for next frame
    private final Map<Integer, NetworkActor> actors = new HashMap<>();  // ID -> NetworkActor map
    private PowerData powerState;

    public GameState()
    {
        //  Add your ClientActions here!  //
        actionManager.put(PacketAction.ADD, AddActorAction.class);
        actionManager.put(PacketAction.UPDATE, UpdateActorAction.class);
        actionManager.put(PacketAction.POWER, UpdatePowerAction.class);
        // End ClientAction instantiation //
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
        for (int i = 0; i < updates.size(); i++)
        {
            Packet packet = new Packet(updates.remove());

            // Get action and type
            ClientAction action = actionManager.get(packet.getAction());
            action.act(this, packet);
        }
    }

    public void addUpdate(String update)
    {
        updates.add(update);
    }

    public void addActor(int id, NetworkActor actor)
    {
        actors.put(id, actor);
    }

    public List<NetworkActor> getActors()
    {
        return Collections.unmodifiableList(new ArrayList<>(actors.values()));
    }

    public NetworkActor getActor(int id)
    {
        return actors.get(id);
    }

    public void setPowerState(PowerData powerState)
    {
        this.powerState = powerState;
    }

    public PowerData getPowerState()
    {
        return powerState;
    }
}