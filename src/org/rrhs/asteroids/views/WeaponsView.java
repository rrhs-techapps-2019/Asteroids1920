package org.rrhs.asteroids.views;

import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.network.Client;

public class WeaponsView extends GameView
{
    //To DO:
    // [ ] Make A Small White Line That Will Delete itself after 10 seconds
    // [ ] Move turret
    // [ ] detect direction of turret
    // [ ] Reload meter (talk with Eng. group to find out how long to reload)
    // [ ] Aiming Retical (Different Color? is it different because of energy?)
    // [ ] Talk With Ast. group about how astroids will ushinteract with our bullets and how energy can change that
    public WeaponsView(Client client, GameState state)
    {
        super(client, state);
    }

    // Makes turret fire laser
    public void fireLaser()
    {
    }

    // Gets status of reload meter
    public int getReload()
    {
        return -1;
    }

    // Clears the reload meter
    public void clearReload()
    {
    }

    // Charges the reload meter based on weapon system's energy (parameter might change)
    public void reload(int energy)
    {
    }

    // Gets the rotation of the turrent (To figure out where turret is relative to the ship?)
    public int getRotation()
    {
        return -1;
    }
}