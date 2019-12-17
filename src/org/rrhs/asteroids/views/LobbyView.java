package org.rrhs.asteroids.views;

import mayflower.Keyboard;
import mayflower.Mayflower;
import mayflower.World;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerOffline;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.network.Server;


public class LobbyView extends World {
    boolean wview = false;
    boolean pview = false;
    boolean sview = false;
    boolean eview = false;
    public LobbyView(Client client, GameState state) {

    }
    @Override
    public void act() {
        super.showText("CHOOSE YOUR POSITION",50,50);

        if(!wview) super.showText("WEAPONS PRESS 1",50,100);
        if(!pview)super.showText("PILOT PRESS 2",50,150);
        if(!sview)super.showText("SENSORS PRESS 3",50,200);
        if(!eview)super.showText("ENGINEER PRESS 4",50,250);
        if(Mayflower.isKeyPressed(Keyboard.KEY_1))
        {
            super.removeText(50,100);
            super.showText("Please Hold Weapons", 50, 100);
            wview = true;
        }
        if(Mayflower.isKeyPressed(Keyboard.KEY_2))
        {
            super.removeText(50, 150);
            super.showText("Please Hold Pilot", 50, 150);
            pview = true;
        }
        if(Mayflower.isKeyPressed(Keyboard.KEY_3))
        {
            super.removeText(50, 200);
            super.showText("Please Hold Sensors", 50, 200);
            sview = true;
        }
        if(Mayflower.isKeyPressed(Keyboard.KEY_4))
        {
            super.removeText(50, 250);
            super.showText("Please Hold Engineer", 50, 250);
            eview = true;
        }
    }
    public static void main(String[] args) {
        new RunnerOffline(LobbyView.class);
    }
}