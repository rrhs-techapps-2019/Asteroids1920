package org.rrhs.asteroids.actors.objects;

import mayflower.MayflowerImage;
import org.rrhs.asteroids.actors.NetworkActor;

public class Asteroid extends NetworkActor
{
    public Asteroid(int id)
    {
        super(id, "asteroid");
        MayflowerImage img = new MayflowerImage("img/Asteroid.png");
        img.scale(.2);
        setImage(img);
    }

    public void act()
    {
        if (this.isTouching(Ship.class))
        {
            this.setRotation(this.getRotation() + 180);
        }
        super.act();

        // TODO: fix infinite loop
        /*while (isAtEdge())
        {
            turn(10);
            move(1);
        }*/
        super.act();
    }
}