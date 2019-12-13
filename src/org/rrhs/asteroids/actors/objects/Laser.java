package org.rrhs.asteroids.actors.objects;

import mayflower.Actor;
import mayflower.MayflowerImage;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.ServerWorld;

public class Laser extends NetworkActor {
    public Laser(int id) {
        super(id, "laser");
        MayflowerImage img = new MayflowerImage("img/Laser.png");
        img.scale(.2);
        setImage(img);
        // doubles speed?
        this.speed = speed + speed;
    }

//chekcs if toutching Astroid or Ededge of screen and removes its self
    public void act() {
        super.act();

        Actor[] touching = getTouching();

        for (Actor laser : touching) {
            if (laser touching instanceof AsteroidBig)
            {
                ServerWorld w = getServerWorld();

                w.removeActor(this);
            }
            if (lazer touching instanceof AsteroidSmall)
            {
                ServerWorld w = getServerWorld();

                w.removeActor(this);
            }
        }
    }

    {
        super.act();

        while (isAtEdge())
        {
            ServerWorld w = getServerWorld();
            w.removeActor(this);
        }
    }
}


