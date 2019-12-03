package org.rrhs.asteroids.views;

import mayflower.*;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.actors.NetworkActor;

import java.util.ArrayList;
import java.util.HashMap;

public class FirstPersonView extends World {
    private GameState gameState;
    private HashMap<NetworkActor, FPAsteroidActor> asteroidMap;
    private ArrayList<FPAsteroidActor> asteroidCache;

    private static final int INIT_CACHE_SIZE = 8;

    public FirstPersonView(GameState gameState) {
        this.gameState = gameState;
        this.asteroidMap = new HashMap<>();
        this.asteroidCache = new ArrayList<>(INIT_CACHE_SIZE);

        for (int i = 0; i < INIT_CACHE_SIZE; i++) {
            asteroidCache.add(new FPAsteroidActor());
        }

        setBackground(new MayflowerImage(getWidth(), getHeight(), Color.BLACK));
    }

    public void act() {

    }

    class SpaceBG extends Actor {
        public SpaceBG() {
            MayflowerImage m = new MayflowerImage("img/space_bg.png");
            m.scale(Mayflower.getWidth(), Mayflower.getHeight());
            setImage(m);
        }

        public void act() {}
    }

    // Wrapper for asteroid NetworkActors that can be rendered in different positions than the NetworkActor is storing //
    class FPAsteroidActor extends Actor {
        public FPAsteroidActor() {

        }

        public void act() {

        }
    }
}