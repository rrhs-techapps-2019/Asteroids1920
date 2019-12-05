package org.rrhs.asteroids.actors;

import mayflower.MayflowerImage;

public class Turret extends NetworkActor
{
    public Turret(int id)
    {
        super(id, "turret");
        MayflowerImage img = new MayflowerImage("img/Turret.png");
        img.scale(.2);
        setImage(img);
    }
}