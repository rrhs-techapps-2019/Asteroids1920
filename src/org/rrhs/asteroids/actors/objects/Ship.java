package org.rrhs.asteroids.actors.objects;


import mayflower.MayflowerImage;


import mayflower.*;
import org.rrhs.asteroids.actors.NetworkActor;

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


    public void move()
    {
        move();
    }

    public void turn()
    {
        act();
    }

    public void main(String[] args)
    {
        update();

    }

    public int rotate()
    {
        if (Mayflower.isKeyDown(Keyboard.KEY_LEFT) && !Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
        {
            this.turn(-getRotationSpeed());
            return 1;
        } else if (!Mayflower.isKeyDown(Keyboard.KEY_LEFT) && Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
        {

        }
        if (Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && !Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
        {
            this.turn(getRotationSpeed());
            return 1;
        }
        if (!Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
        {

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