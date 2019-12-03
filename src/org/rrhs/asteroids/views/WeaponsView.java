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
    // [ ] Asteroids will break into 3 pieces when shot 1 time and the smaller asteroids will be destroyed after 1 shot as well.
	
	int direction;
	int energy;
	int reloadTime;

    public WeaponsView(Client client, GameState state)
    {
        super(client, state);
		direction = 0;
		energy = 0;
		reloadTime = 0;
    }

    // Makes turret fire laser
    public void fireLaser()
    {
        if (isReloadReady()) {Laser pew = new Laser();}
    }

    // Returns if turrent is ready to reload
    // If reloadTime is 100% => True; else false
    private boolean isReloadReady()
    {return (this.reloadTime == 100) ? true : false;}

    // Clears the reload meter
    public void clearReload()
    {this.reloadTime = 0;}

    // Charges the reload meter based on weapon system's energy (parameter might change)
    public void reload()
    {
		
    }

    // Gets the rotation of the turret (0-359 will be the direction ship is facing)
    public int getDirection()
    {
        return 0;
    }
}

