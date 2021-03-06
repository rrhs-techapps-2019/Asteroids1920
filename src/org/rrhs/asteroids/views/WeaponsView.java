package org.rrhs.asteroids.views;

import mayflower.Keyboard;
import mayflower.Mayflower;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;

public class WeaponsView extends GameView
{
    //To DO:
    // [ ] Make A Small White Line That Will Delete itself after 10 seconds
    // [ ] Move turret
    // [ ] detect direction of turret
    // [ ] Reload meter (talk with Eng. group to find out how long to reload)
    // [ ] Aiming Retical (Different Color? is it different because of energy?)
    // [ ] Asteroids will break into 3 pieces when shot 1 time and the smaller asteroids will be destroyed after 1 shot as well.

    private int energy;
    private int fireDelay;
    private Client client;
    private Cooldown cooldown;

    public WeaponsView(Client client, GameState state)
    {
        super(client, state);
        energy = 0;
        fireDelay = 10;
        this.cooldown = new Cooldown(0);
    }


    @Override
    public void update()
    {
        this.energy = state.getPowerState().getWeaponsPower();
        if (this.cooldown != null)
        {
            this.cooldown.updateCooldown(convertEnergyToSecs(this.energy));
            this.cooldown.run();
            if (this.cooldown.isCooldownDone())
            {
                this.cooldown = null;
            }
        }
    }

    private double convertEnergyToSecs(int energy)
    {
        return (100 - energy) * .1 + .5;
    }


    private void processInput()
    {

        if (cooldown != null && Mayflower.isKeyDown(Keyboard.KEY_SPACE) && !Mayflower.wasKeyDown(Keyboard.KEY_SPACE))
        {
            this.cooldown = new Cooldown(10);
            Packet fire = new Packet(PacketAction.FIRE);
            client.send(fire);
        }
        // turn left
        if (Mayflower.isKeyDown(Keyboard.KEY_LEFT) && !Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
        {
            Packet turnLeft = new Packet(PacketAction.TURRET_TURN, "left");
            client.send(turnLeft);
        } else if (!Mayflower.isKeyDown(Keyboard.KEY_LEFT) && Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
        {
            Packet stop = new Packet(PacketAction.TURRET_STOP);
            client.send(stop);
        }

        // turn right
        if (Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && !Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
        {
            Packet turnRight = new Packet(PacketAction.TURRET_TURN, "right");
            client.send(turnRight);
            client.send("turnTurret right");
        } else if (!Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
        {
            Packet stop = new Packet(PacketAction.TURRET_STOP);
            client.send(stop);
        }

    }

    static class Cooldown
    {
        private int cooldownSec;
        private int counter;

        public Cooldown(int cooldownSec)
        {
            // Seconds * 60 frames
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


        public void updateCooldown(double secs)
        {
            this.counter = (int) Math.round(counter * (secs * 60 / this.cooldownSec));
            this.cooldownSec = (int) secs * 60;
        }

        public boolean isCooldownDone()
        {
            return counter >= cooldownSec;
        }
    }
}