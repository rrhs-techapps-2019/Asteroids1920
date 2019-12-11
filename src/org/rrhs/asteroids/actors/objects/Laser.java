package org.rrhs.asteroids.actors.objects;

import mayflower.Actor;
import mayflower.MayflowerImage;
import mayflower.World;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.Ship;

import java.util.List;

public class Laser extends NetworkActor
{
    private final Ship from;
    private final String direction;

    public Laser(Ship from, String direction, int id)
    {
        super(id, "laser");
        this.direction = direction;
        this.from = from;

        MayflowerImage pic = new MayflowerImage("");
        pic.scale(1.0);
        this.setImage(pic);
    }

    @Override
    public void act()
    {
        super.act();
    }

    public void update()
    {
        List<Actor> touching = getIntersectingObjects(Actor.class);

        for (Actor lazer : touching)
        {
            if (lazer instanceof AsteroidBig || lazer instanceof AsteroidSmall)
            {
                World w = getWorld();
                w.removeObject(lazer);
                w.removeObject(this);
                break;
            }
        }
    }
}