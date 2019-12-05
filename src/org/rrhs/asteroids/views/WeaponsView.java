package org.rrhs.asteroids.views;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.network.Client;
import java.util.Timer;
import java.util.TimerTask;

public class WeaponsView extends GameView
{
    //To DO:
    // [ ] Make A Small White Line That Will Delete itself after 10 seconds
    // [ ] Move turret
    // [ ] detect direction of turret
    // [ ] Reload meter (talk with Eng. group to find out how long to reload)
    // [ ] Aiming Retical (Different Color? is it different because of energy?)
    // [ ] Asteroids will break into 3 pieces when shot 1 time and the smaller asteroids will be destroyed after 1 shot as well.

	int energy;
	int reloadTime;

    public WeaponsView(Client client, GameState state)
    {
        super(client, state);
		energy = 0;
		reloadTime = 10;
		Timer timer = new Timer();
		TimerTask task = new Cooldown(reloadTime);
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

    private void processInput()
    {
        if (Mayflower.isKeyDown())
        // turn left
        if (Mayflower.isKeyDown(Keyboard.KEY_LEFT) && !Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
        {
            client.send("turnTurret left");
        } else if (!Mayflower.isKeyDown(Keyboard.KEY_LEFT) && Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
        {
            client.send("stop turnTurret");
        }

        // turn right
        if (Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && !Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
        {
            client.send("turnTurret right");
        } else if (!Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
        {
            client.send("stop turnTurret");
        }

    }
}

private class Cooldown extends TimerTask
{
    private int cooldownSec;
    private int counter;

    public cooldown(int cooldownSec)
    {
        this.cooldownSec = cooldownSec * 60;
        this.counter = 0;
    }

    public void run()
    {
        if (counter < cooldownSec)
        {
            counter++;
        }
    }

    public boolean isCooldownDone()
    {
        return counter == cooldownSec
    }
}
