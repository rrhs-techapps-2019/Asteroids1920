package org.rrhs.asteroids.actors.objects;

import mayflower.Actor;
import mayflower.MayflowerImage;
import mayflower.World;
import org.rrhs.asteroids.actors.NetworkActor;

import java.util.List;

public class Laser extends NetworkActor
{
    public Laser(int id)
    {
        super(id, "laser");
        MayflowerImage img = new MayflowerImage("img/Laser.png");
        img.scale(.2);
        setImage(img);
    }

    @Override
    public void act()
    {
        super.act();
    }

    public void update()
    {
        move(5);
        List<Actor> touching = getIntersectingObjects(Actor.class);

        for (Actor laser : touching)
        {
            if (touching instanceof AsteroidBig)
            {
                World w = getWorld();

                w.removeObject(this);
            }
            if (touching instanceof AsteroidSmall)
            {
                World w = getWorld();

                w.removeObject(this);
            }
        }

        while (isAtEdge())
        {
            World w = getWorld();
            w.removeObject(this);
        }
    }
}



