package org.rrhs.asteroids.views;

import mayflower.Keyboard;
import mayflower.Mayflower;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.network.Client;

public class LobbyView extends GameView {
    public LobbyView(Client client, GameState state) {
        super(client, state);
    }

    @Override
    public void act() {
        super.showText("CHOOSE YOUR POSITION",50,50);
        super.showText("WEAPONS PRESS 1",50,100);
        super.showText("PILOT PRESS 2",50,150);
        super.showText("SENSORS PRESS 3",50,200);
        super.showText("ENGINEER PRESS 4",50,250);
        if(Mayflower.isKeyPressed(Keyboard.KEY_1))
        {
            removeText(50,100);
            Temp("WEAPONS");
        }
        if(Mayflower.isKeyPressed(Keyboard.KEY_2))
        {
            removeText(50, 150);
            Temp("PILOT");
        }
        if(Mayflower.isKeyPressed(Keyboard.KEY_3))
        {
            removeText(50, 200);
            Temp("SENSORS");
        }
        if(Mayflower.isKeyPressed(Keyboard.KEY_4))
        {
            removeText(50, 250);
            Temp("ENGINEER");
        }
    }
    public void Temp(String mc){
        super.showText("PLEASE HOLD" + mc, 50, 50);
        super.showText("OTHERS ARE CHOOSING", 50, 100);
    }
}