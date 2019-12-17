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
    private double testCounter;

    private static final int INIT_CACHE_SIZE = 8;
    private static final int FOV_ANGLE = 90; // Measured in degrees
    private static final double SPRITE_SCALE = 600;

    public FirstPersonView(Client client, GameState gameState) {
        super(client, gameState);
        testRot = 0;
        testCounter = 50;
        this.gameState = gameState;
        this.asteroidMap = new HashMap<>();
        this.asteroidCache = new ArrayList<>(INIT_CACHE_SIZE);

        for (int i = 0; i < INIT_CACHE_SIZE; i++) {
            asteroidCache.add(new FPAsteroidActor());
        }

        setBackground(new MayflowerImage(getWidth(), getHeight(), Color.BLACK));

        fpaa = new FPAsteroidActor();
        addObject(fpaa, -1000, 0);
    }

    public void act() {
        //testCounter -= 0.1;

        double xo = testCounter;
        double yo = 0;
        int asteroidX = getAsteroidX(xo, yo);
        //System.out.println(testCounter);
        double scale = Math.min(getScaleFactor(xo, yo) * 64, 1000);
        //System.out.println("rot: " + testRot + " deg, asteroidx: " + asteroidX + ", scale: " + scale);

        MayflowerImage img = new MayflowerImage("img/Asteroid.png");
        img.scale((int) scale, (int) scale);
        fpaa.setImage(img);
        fpaa.setLocation(asteroidX - (fpaa.getImage().getWidth() / 2), getWidth() / 2 - (fpaa.getImage().getHeight() / 2));

        if (Mayflower.isKeyDown(Keyboard.KEY_LEFT)) {
            if (testRot == 360) {
                testRot = 0;
            }

            testRot = testRot + 1;
        } else if (Mayflower.isKeyDown(Keyboard.KEY_RIGHT)) {
            if (testRot == 0) {
                testRot = 360;
            }

            testRot = testRot - 1;
        } else if (Mayflower.isKeyDown(Keyboard.KEY_DOWN)) {
            testCounter -= 0.1;
        } else if (Mayflower.isKeyDown(Keyboard.KEY_UP)) {
            testCounter += 0.1;
        }
    }

    //returns the x-coordinate of an asteroid's sprite on the SSQ
    public int getAsteroidX(double trueX, double trueY) {
        /*double θdeg = calculateRotation() % 360;
        double θ = Math.toRadians(θdeg);
        double relativeY = (trueY)*Math.cos(θ) + (trueX)*Math.sin(θ);

        double φ = Math.atan2(trueY, trueX);
        double ρ = Math.toRadians(FOV_ANGLE);
        double μωμ = 0.5;*/
        //double x = getWidth() * (((θ - φ) / ρ) /*- μωμ*/);
        /*double x = getWidth() * (((θ - φ) / ρ) + μωμ);
        System.out.println("rot: " + +θdeg + " deg, asteroidx: " + x + ", rel y: " + relativeY);

        if (relativeY <= 0) {
            //return Integer.MAX_VALUE;
        } else {
            //return (int) x;
        }
        return (int) x;*/

        double θdeg = calculateRotation() % 360;









        double θ = Math.toRadians(θdeg);
        double ρ = Math.toRadians(FOV_ANGLE);
        double φ = Math.atan2(trueY, trueX);
        double π = Math.PI;
        double τ = 2*π;
        φ = (τ + φ) % τ;

        double ξ = (θ - φ) % τ;
        if (ξ > π) {
            ξ -= τ;
        }

        double μωμ = 0.5;
        double x = getWidth() * ((ξ / ρ) + μωμ);

        System.out.println("rot: " + +θdeg + " deg, asteroidx: " + x);

        return (int) x;
    }

    //returns the factor by which the asteroid sprite should be scaled for the perspective view
    public double getScaleFactor(double x, double y) {
        return Math.max(0.01, SPRITE_SCALE/(Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0)) * FOV_ANGLE));
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
