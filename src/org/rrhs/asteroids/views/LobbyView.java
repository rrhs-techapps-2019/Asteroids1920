package org.rrhs.asteroids.views;

import mayflower.World;
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
        super.showText("ENGINEER PRESS 4",50,100);
    }
}