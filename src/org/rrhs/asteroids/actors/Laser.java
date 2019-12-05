package org.rrhs.asteroids.actors;

import mayflower.MayflowerImage;

public class Laser extends NetworkActor {
    public Laser(int id) {
        super(id, "laser");
        MayflowerImage img = new MayflowerImage("img/Laser.png");
        img.scale(.2);
        setImage(img);
        this.speed = speed + speed;
    }


    public void update() {
        super.update();

        Actor[] touching = getTouching();

        for (Actor laser : touching) {
            if (laser instanceof AsteroidBig) {
                ServerWorld W = getServerWorld();

                s.removeActor(this);
            }
            if (lazer instanceof AsteroidSmall) {
                ServerWorld W = getServerWorld();

                w.removeActor(this);
            }
        }
    }

    {
        super.act();

        while (isAtEdge())
        {
            ServerWorld W = getServerWorld();
            w.removeActor(this);
        }
    }
}


