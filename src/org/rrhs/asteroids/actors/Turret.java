package org.rrhs.asteroids.actors;

import mayflower.MayflowerImage;
import org.rrhs.asteroids.network.ServerWorld;

public class Turret extends NetworkActor
{
    public Turret(int id)
    {
        super(id, "turret");
        MayflowerImage img = new MayflowerImage("img/Turret.png");
        img.scale(.2);
        setImage(img);
    }
    public void update() {
        super.update();
        ServerWorld w = getServerWorld();
        int shipX = w.ship.GetX();
        int shipY = w.ship.Gety();
    this.setLocation(shipX, shipY);

    }
}