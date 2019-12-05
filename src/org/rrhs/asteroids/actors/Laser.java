package org.rrhs.asteroids.actors;

import mayflower.MayflowerImage;

public class Laser extends NetworkActor
{
        public Laser( Ship from, String direction)
        {
        this.direction = direction;
        this.from = from;
        Picture pic = new Picture("");
        pic.resize(NA, NA);
        pic.setBounds(NA, NA, NA, NA);
        setPicture(pic);

        }
    public void update()
    {
        super.update();

        Actor[] touching = getTouching();

        for(Actor lazer : touching)
        {
            if(lazer  instanceof AsteroidBig)
            {
                ServerWorld w = getServerWorld();

                w.removeActor(this);
            }
            if(lazer  instanceof AsteroidSmall)
            {
                ServerWorld w = getServerWorld();
                w.removeActor(AstroidSmall);
                w.removeActor(this);
            }
        }


    }