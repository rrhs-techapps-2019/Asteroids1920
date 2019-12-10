package org.rrhs.asteroids.views;

import mayflower.*;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.actors.NetworkActor;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class FirstPersonView extends GameView {
    private GameState gameState;
    private HashMap<NetworkActor, FPAsteroidActor> asteroidMap;
    private ArrayList<FPAsteroidActor> asteroidCache;

    private static final int INIT_CACHE_SIZE = 8;
    private static final int FOCAL_LENGTH = 1;
    private static final double UNITS_TO_PIXELS = 0.1;

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

    //returns the x-coordinate of an asteroid's sprite on the screen
    public int getAsteroidX(int trueX, int trueY) {
	double rot = Math.toRadians(calculateRotation());
	double x = ((double) trueX)*Math.cos(rot) - ((double) trueY)*Math.sin(rot);
	double y = ((double) trueY)*Math.cos(rot) + ((double) trueX)*Math.cos(rot);

	return (int)((getWidth()/2.0) + FOCAL_LENGTH*(x/y)*UNITS_TO_PIXELS);
    }

    //returns the factor by which the asteroid sprite should be scaled for the perspective view
    public double getScaleFactor(int trueX, int trueY) {
	double rot = Math.toRadians(calculateRotation());
	double x = ((double) trueX)*Math.cos(rot) - ((double) trueY)*Math.sin(rot);
	double y = ((double) trueY)*Math.cos(rot) + ((double) trueX)*Math.cos(rot);

	return Math.sqrt((Math.pow(FOCAL_LENGTH*x/y, 2.0) + Math.pow(FOCAL_LENGTH, 2.0))
			/(Math.pow(x, 2.0) + Math.pow(y, 2.0)));
    }

    //pilot: returns ship rotation
    //weapons: returns ship rotation + view rotation relative to ship
    public abstract double calculateRotation();

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
