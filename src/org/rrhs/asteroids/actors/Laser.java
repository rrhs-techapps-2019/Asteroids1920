package org.rrhs.asteroids.actors;

import mayflower.MayflowerImage;

import java.util.Timer;
import java.util.TimerTask;

public class Laser extends NetworkActor
{
    // TO DO:
    // [ ] Move laser in space
    // [ ] Add collsion event with asteroid
    // [ ] Add decay to laser?
    int SecPassed = 0;
    Timer timer = new Timer();
    TimerTask task = new TimerTask()
    {
        @Override
        public void run()
        {

        }
    };

    public Laser(int id)
    {
        super(id, "laser");
        MayflowerImage img = new MayflowerImage("Laser.png");
        setImage(img);
    }

    public void run()
    {
        SecPassed++;

    }

    public void start()
    {
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
}