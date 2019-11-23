package org.rrhs.asteroids.actors;

import mayflower.MayflowerImage;

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