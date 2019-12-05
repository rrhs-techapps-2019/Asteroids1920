package org.rrhs.asteroids.actors.objects;

import mayflower.MayflowerImage;
import org.rrhs.asteroids.actors.NetworkActor;

public class Ship extends NetworkActor
{
    public Ship(int id)
    {
        super(id, "ship");
        MayflowerImage img = new MayflowerImage("img/Ship.png");
        img.scale(.2);
        setImage(img);
    }
}