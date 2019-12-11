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
    //update calls the location of the ship and moves the turret with the ship
    public void update() {
        super.update();
        ServerWorld w = getServerWorld();
        int shipX = w.ship.GetX();
        int shipY = w.ship.Gety();
    this.setLocation(shipX, shipY);

 //Might need to use thi to turn in the future, Might not need
    if(Mayflower.isKeyDown(Keyboard.KEY_LEFT) && !Mayflower.wasKeyDown(Keyboard.KEY_LEFT))
    {
        setRotation(getRotation() + 10);
    }

        if(Mayflower.isKeyDown(Keyboard.KEY_RIGHT) && !Mayflower.wasKeyDown(Keyboard.KEY_RIGHT))
        {
            setRotation(getRotation() - 10);

        }

    }
}