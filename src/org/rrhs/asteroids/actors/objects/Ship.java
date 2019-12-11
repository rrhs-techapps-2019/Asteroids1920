package org.rrhs.asteroids.actors.objects;

import mayflower.MayflowerImage;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.data.ShipData;

public class Ship extends NetworkActor
{
    private ShipData data;

    public Ship(int id)
    {
        super(id, "ship");
        MayflowerImage img = new MayflowerImage("img/Ship.png");
        img.scale(.2);
        setImage(img);
    }

    public ShipData getData()
    {
        return data;
    }
}