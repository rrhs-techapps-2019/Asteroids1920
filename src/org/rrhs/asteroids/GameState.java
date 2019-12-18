package org.rrhs.asteroids;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.data.PowerData;
import org.rrhs.asteroids.actors.data.RoleData;
import org.rrhs.asteroids.network.ActionManager;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.network.actions.client.*;

import java.util.*;

/**
 * Manages updates and actor IDs.
 */
public class GameState
{
    private final ActionManager<ClientAction> actionManager = new ActionManager<>();
    private final Queue<String> updates = new LinkedList<>();           // Updates queued for next frame
    private final Map<Integer, NetworkActor> actors = new HashMap<>();  // ID -> NetworkActor map
    private RoleData roleState = new RoleData();
    private PowerData powerState;
    private int clientId;

    public GameState()
    {
        //  Add your ClientActions here!  //
        actionManager.put(PacketAction.ADD, AddActorAction.class);
        actionManager.put(PacketAction.UPDATE, UpdateActorAction.class);
        actionManager.put(PacketAction.POWER, UpdatePowerAction.class);
        actionManager.put(PacketAction.UPDATE_ROLES, UpdateRolesAction.class);
        actionManager.put(PacketAction.IDENTIFY, IdentAction.class);
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

    public Collection<NetworkActor> getActors()
    {
        return Collections.unmodifiableCollection(actors.values());
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

    public void setRoleState(RoleData roleState)
    {
        this.roleState = roleState;
    }

    public RoleData getRoleState()
    {
        return roleState;
    }

    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }

    public int getClientId()
    {
        return clientId;
    }
}