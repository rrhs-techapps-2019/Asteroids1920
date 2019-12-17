package org.rrhs.asteroids.actors.objects;


import mayflower.Keyboard;
import mayflower.Mayflower;
import mayflower.MayflowerImage;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.util.MayflowerUtils;

public class Ship extends NetworkActor
{
    private int accelerationUp;
    private int accelerationDown;
    private int speed;
    private int rotationSpeed;
    private int energy;


    public Ship(int id)
    {
        super(id, "ship");
        MayflowerImage img = new MayflowerImage("img/Ship.png");
        img.scale(.2);
        setImage(img);
        speed = getSpeed();
        rotationSpeed = getRotationSpeed();
        energy = 1;
        accelerationUp = 0;
        accelerationDown = 0;

    }

    public boolean movingUp()
    {
        if (getSpeed() > 0)
        {
            return true;
        }
        return false;
    }

    public int rotate()
    {
        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_LEFT))
        {
            this.turn(-getRotationSpeed());
            return 1;
        } else if (MayflowerUtils.wasKeyReleased(Keyboard.KEY_LEFT))
        {

        }

        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_RIGHT))
        {
            this.turn(getRotationSpeed());
            return 1;
        }
        return 0;
    }

    public void update()
    {
        int i = 0;
        if ((!Mayflower.wasKeyDown(Keyboard.KEY_LSHIFT) || !Mayflower.wasKeyDown(Keyboard.KEY_RSHIFT)) || (!Mayflower.isKeyDown(Keyboard.KEY_LSHIFT) || !Mayflower.isKeyDown(Keyboard.KEY_RSHIFT)))
        {
            accelerationUp = 0;
            accelerationDown = 0;
        }


        while ((!Mayflower.isKeyDown(Keyboard.KEY_UP)) && (!Mayflower.isKeyDown(Keyboard.KEY_DOWN)))
        {
            move();
        }
        while ((Mayflower.isKeyDown(Keyboard.KEY_UP)))
        {
            i = rotate();
            if ((Mayflower.isKeyDown(Keyboard.KEY_LSHIFT) || Mayflower.isKeyDown(Keyboard.KEY_RSHIFT)))
            {
                accelerationUp = accelerationUp + energy;

            }
            setSpeed(accelerationUp + speed);
            speed = speed + accelerationUp;
            move();
        }
        while ((Mayflower.isKeyDown(Keyboard.KEY_DOWN)))
        {
            i = rotate();
            if ((Mayflower.isKeyDown(Keyboard.KEY_LSHIFT) || Mayflower.isKeyDown(Keyboard.KEY_RSHIFT)))
            {
                accelerationDown = accelerationDown - energy;

            }
            setSpeed(accelerationDown + speed);
            speed = speed + accelerationDown;
            move();
        }

        if (i == 0)
        {
            rotate();
        }
    }

}