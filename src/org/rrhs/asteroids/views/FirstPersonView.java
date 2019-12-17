package org.rrhs.asteroids.views;

import mayflower.*;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.network.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class FirstPersonView extends GameView {
    private GameState gameState;
    private HashMap<NetworkActor, FPAsteroidActor> asteroidMap;
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

        setBackground(new MayflowerImage(getWidth(), getHeight(), Color.BLACK));
    }

    public void act() {
        for (Map.Entry<NetworkActor, FPAsteroidActor> entry : asteroidMap.entrySet()) {
            NetworkActor networkData = entry.getKey();
            FPAsteroidActor actor = entry.getValue();

            double xo = networkData.getX() - shipXGoesHere;
            double yo = networkData.getY() - shipYGoesHere;

            int asteroidX = getAsteroidX(xo, yo);
            double scale = getScaleFactor(xo, yo) * 64;
            MayflowerImage img = new MayflowerImage("img/Asteroid.png");
            img.scale((int) scale, (int) scale);
            actor.setImage(img);
            actor.setLocation(asteroidX - (actor.getImage().getWidth() / 2), getWidth() / 2 - (actor.getImage().getHeight() / 2));
        }
    }

    //returns the x-coordinate of an asteroid's sprite on the SSQ
    public int getAsteroidX(double trueX, double trueY) {
        double γ = calculateRotation() % 360;
        double θ = Math.toRadians(γ);
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
        double χ = getWidth() * ((ξ / ρ) + μωμ);

        System.out.println("rot: " + γ + " deg, asteroidx: " + χ);

        return (int) χ;
    }

    //returns the factor by which the asteroid sprite should be scaled for the perspective view
    public double getScaleFactor(double x, double y) {
        return Math.min(Math.max(0.01, SPRITE_SCALE/(Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0)) * FOV_ANGLE)), 1000s);
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
            setImage(img);
        }

        public void act() {

        }
    }
}
