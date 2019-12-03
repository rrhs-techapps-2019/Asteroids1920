package org.rrhs.asteroids.actors;

import mayflower.MayflowerImage;

public class Laser
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
                Stage s = getStage();
                s.removeActor(AstroidBig);
                s.add(AstroidSmall);
                s.add(AstroidSmall);
                s.removeActor(this);
            }
            if(lazer  instanceof AsteroidSmall)
            {
                Stage s = getStage();
                s.removeActor(AstroidSmall);
                s.removeActor(this);
            }
        }


    }