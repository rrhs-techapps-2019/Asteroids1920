package org.rrhs.asteroids.actors;

import mayflower.MayflowerImage;

public class Laser extends NetworkActor
{
  // TO DO:
  // Move laser in space
  // Add collsion event with asteroid
    public Laser(int id) {
        super(id, "laser");
        MayflowerImage img = new MayflowerImage("Laser.png");
        setImage(img);
    }
}