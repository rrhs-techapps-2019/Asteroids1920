package org.rrhs.asteroids.views;

import mayflower.*;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Client;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class FirstPersonView extends GameView {
    private GameState gameState;
    private HashMap<NetworkActor, FPAsteroidActor> asteroidMap;
    private ArrayList<FPAsteroidActor> asteroidCache;
    private FPAsteroidActor fpaa;
    private int testRot;

    private static final int INIT_CACHE_SIZE = 8;
    private static final int FOCAL_LENGTH = 20;
    private static final double UNITS_TO_PIXELS = 0.1;
    private static final double SPRITE_SCALE = 1.0;

    public FirstPersonView(Client client, GameState gameState) {
        super(client, gameState);
        testRot = 0;
        this.gameState = gameState;
        this.asteroidMap = new HashMap<>();
        this.asteroidCache = new ArrayList<>(INIT_CACHE_SIZE);

        for (int i = 0; i < INIT_CACHE_SIZE; i++) {
            asteroidCache.add(new FPAsteroidActor());
        }

        setBackground(new MayflowerImage(getWidth(), getHeight(), Color.WHITE));

        fpaa = new FPAsteroidActor();
        addObject(fpaa, -1000, 0);
    }

    public void act() {
        testRot = (testRot + 1) % 360;
        int xo = 5;
        int yo = 0;
        int asteroidX = getAsteroidX(xo, yo);
        double scale = Math.min(getScaleFactor(xo, yo) * 64, 1000);
        System.out.println("rot: " + testRot + " deg, asteroidx: " + asteroidX + ", scale: " + scale);

        //MayflowerImage img = new MayflowerImage("img/Asteroid.png");
        //img.scale(scale, scale);
        //fpaa.setImage(img);
        //fpaa.getImage().scale(scale, scale);
        fpaa.setLocation(asteroidX - (fpaa.getImage().getWidth() / 2), getWidth() / 2 - (fpaa.getImage().getHeight() / 2));
    }

    //returns the x-coordinate of an asteroid's sprite on the SSQ
    public int getAsteroidX(int trueX, int trueY) {
        double rot = Math.toRadians(calculateRotation());
        double x = ((double) trueX)*Math.cos(rot) - ((double) trueY)*Math.sin(rot);
        double y = ((double) trueY)*Math.cos(rot) + ((double) trueX)*Math.sin(rot);
	if(y <= 0) {
	    return Integer.MIN_VALUE;
	}
        System.out.println(x + ", " + y);

        return (int)((getWidth()/2.0) + FOCAL_LENGTH*(x/y)*UNITS_TO_PIXELS);
    }

    //returns the factor by which the asteroid sprite should be scaled for the perspective view
    public double getScaleFactor(int trueX, int trueY) {
        double rot = Math.toRadians(calculateRotation());
        double x = ((double) trueX)*Math.cos(rot) - ((double) trueY)*Math.sin(rot);
        double y = ((double) trueY)*Math.cos(rot) + ((double) trueX)*Math.sin(rot);

        //return Math.sqrt((Math.pow(FOCAL_LENGTH*x/y, 2.0) + Math.pow(FOCAL_LENGTH, 2.0))
                ///(Math.pow(x, 2.0) + Math.pow(y, 2.0)));
        return SPRITE_SCALE/(Math.pow(x, 2.0) + Math.pow(y, 2.0));
    }

    //pilot: returns ship rotation
    //weapons: returns ship rotation + view rotation relative to ship
    //public abstract double calculateRotation();
    public double calculateRotation() {
        return testRot;
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
            MayflowerImage img = new MayflowerImage("img/Asteroid.png");
            //img.scale(512, 512);
            setImage(img);
            //scale(64, 64);
        }

        public void act() {

        }
    }
}
