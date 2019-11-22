package org.rrhs.asteroids.views;

import mayflower.MayflowerImage;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerOffline;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.objects.Circle;
import org.rrhs.asteroids.network.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Sensors extends GameView
{
    private ArrayList<NetworkActor> asteroids;
    private NetworkActor ship;
    private ArrayList<NetworkActor> visibleAsteroids;
    private int xBase;
    private int yBase;
    private int xCur;
    private int yCur;
    private double scaleFactor;
    private mayflower.Timer time;
    private int power;
    private Circle circle;

    //800 by 600 screen per person
    public Sensors(Client client, GameState state)
    {
        super(client, state);
        asteroids = getAsteroids();
        compareCords();
        xBase = 800;
        yBase = 600;
        xCur = 800;
        yCur = 600;
        scaleFactor = 1.0;
        time = new mayflower.Timer(1000);
        power = 0;
        circle = new Circle();
        compareCords();
        createScreen();
    }

    public static void main(String[] args)
    {
        new RunnerOffline(Sensors.class);
    }

    //gets list of asteroids to use later
    public ArrayList<NetworkActor> getAsteroids()
    {
        ArrayList<NetworkActor> ret = new ArrayList<NetworkActor>();
        List<NetworkActor> actors = super.getState().getActors();
        for (int i = actors.size() - 1; i >= 0; i--)
        {
            NetworkActor n = actors.get(i);
            if ((n.getType().equals("asteroid")))
            {
                ret.add(n);
            }
        }
        return ret;
    }

    public void moveCircle()
    {
        time.set(((100 - power) * 75) + 250);
        if (time.isDone())
        {
            // increase the image size of the circle 

            MayflowerImage img = circle.getImage();
            if (img.getHeight() >= 1.2 * yCur)
            {
                img.scale(40, 40);
            }
            img.scale(1.2);
            circle.setImage(img);
            mayflower.Timer t = new mayflower.Timer(250);
            ArrayList<NetworkActor> added = new ArrayList<NetworkActor>();
            ArrayList<NetworkActor> asters = displayAster();
            for (NetworkActor asteroid : asters)
            {

                addObject(asteroid, asteroid.getX(), asteroid.getY());
                added.add(asteroid);

            }
            if (t.isDone())
            {
                for (NetworkActor actor : added)
                {
                    removeObject(actor);
                }
            }
            time.reset();
        }
    }

    public ArrayList<NetworkActor> displayAster()
    {
        ArrayList<NetworkActor> putAster = new ArrayList<NetworkActor>();
        ArrayList<NetworkActor> asters = new ArrayList<NetworkActor>();
        if (ship != null)
        {
            asters = getAsteroids();
            int xCenter = ship.getX(); //center coordinates
            int yCenter = ship.getY();
            MayflowerImage img = circle.getImage();
            int diameter = img.getHeight(); //length of diameter
            int radius = diameter / 2; //length of radius
            int xLine = radius;
            //For the second quadrant of the circle
            for (int i = xLine; i >= 0; i--) //subtracts the xLine
            {
                int yLine = (int) (Math.sqrt((radius * radius) - (i * i))); //gets the yLine for every xLine
                int yCoor = yCenter + yLine;
                int xCoor = xCenter - xLine;
                //any asteroids in the lists have the yCoor and XCoor, display the asteroid on the screen for 1 s)
                for (int j = asters.size(); j > 0; j--)
                {
                    //if(asters[j].get(x)==xCoor &&  asters[j].get(y)==yCoor)
                    if (asters.get(j).getX() == xCoor && asters.get(j).getY() == yCoor)
                    {
                        putAster.add(asters.get(j));
                    }
                }

            }
            //for the third quadrant of the circle
            for (int i = xLine; i >= 0; i--) //subtracts the xLine
            {
                int yLine = (int) (Math.sqrt((radius * radius) - (i * i))); //gets the yLine for every xLine
                int yCoor = yCenter - yLine;
                int xCoor = xCenter - xLine;
                //any asteroids in the lists have the yCoor and XCoor, display the asteroid on the screen for 1 s)
                for (int j = asters.size(); j > 0; j--)
                {
                    //if(asters[j].get(x)==xCoor &&  asters[j].get(y)==yCoor)
                    if (asters.get(j).getX() == xCoor && asters.get(j).getY() == yCoor)
                    {
                        putAster.add(asters.get(j));
                    }
                }

            }
            //for the first quadrant of the circle
            for (int i = 1; i <= xLine; i++) //increases to the amount of xLine form 0
            {
                int yLine = (int) (Math.sqrt((radius * radius) - (i * i))); //gets the yLine for every xLine
                int yCoor = yCenter + yLine;
                int xCoor = xCenter + i;
                //any asteroids in the lists have the yCoor and XCoor, display the asteroid on the screen for 1 s)
                for (int j = asters.size(); j > 0; j--)
                {
                    //if(asters[j].get(x)==xCoor &&  asters[j].get(y)==yCoor)
                    if (asters.get(j).getX() == xCoor && asters.get(j).getY() == yCoor)
                    {
                        putAster.add(asters.get(j));
                    }
                }

            }
            //for the fourth quadrant of the circle
            for (int i = 1; i <= xLine; i++) //increases to the amount of xLine form 0
            {
                int yLine = (int) (Math.sqrt((radius * radius) - (i * i))); //gets the yLine for every xLine
                int yCoor = yCenter - yLine;
                int xCoor = xCenter + i;
                //any asteroids in the lists have the yCoor and XCoor, display the asteroid on the screen for 1 s)
                for (int j = asters.size(); j > 0; j--)
                {
                    //if(asters[j].get(x)==xCoor &&  asters[j].get(y)==yCoor)
                    if (asters.get(j).getX() == xCoor && asters.get(j).getY() == yCoor)
                    {
                        putAster.add(asters.get(j));
                    }
                }
            }
        }
        return putAster;
    }

    public void act()
    {
        super.act();
        compareCords();
        //createScreen();
        asteroids = getAsteroids();
        moveCircle();
    }

    //public int Screen
    //gets ship coordinates, then compares those to all asteroids
    //if an asteroid would display on the screen, it is added to a list
    public void compareCords()
    {
        Collection<NetworkActor> actors = getState().getActors();
        NetworkActor s = null;
        for (NetworkActor a : actors)
        {
            if (a.getType().equals("ship"))
            {
                s = a;
                break;
            }
        }
        if (s != null)
        {
            ship = s;
            int shipx = ship.getX();
            int shipy = ship.getY();
            visibleAsteroids = new ArrayList<NetworkActor>();
            for (NetworkActor a : asteroids)
            {
                int xdiff = Math.abs(shipx - a.getX());
                int ydiff = Math.abs(shipy - a.getY());
                if (xdiff <= 400 && ydiff <= 300)
                {
                    visibleAsteroids.add(a);
                }
            }
        }

    }

    public void createScreen()
    {
        // for(NetworkActor asteroid : visibleAsteroids){
        // addObject(asteroid,asteroid.getX(),asteroid.getY());
        // }
        if (ship != null)
        {
            addObject(ship, ship.getX(), ship.getY());
        }
        if (circle != null && ship != null)
        {
            addObject(circle, ship.getX(), ship.getY());
        }
    }

    //takes some arguememt which tells it how to alter its magnification
    //positive number moves zooms in, negative zooms out
    //zooms in and out in descrete increments, not proportional to the number input
    public void changeMagnification(int change)
    {
        if (change > 0 && scaleFactor > .2)
        {
            scaleFactor -= .2;
            double x = xBase * scaleFactor;
            double y = yBase * scaleFactor;
            xCur = (int) x;
            yCur = (int) y;
        }
        if (change < 0 && scaleFactor < 2)
        {
            scaleFactor += .2;
            double x = xBase * scaleFactor;
            double y = yBase * scaleFactor;
            xCur = (int) x;
            yCur = (int) y;
        }
    }
    //800 by 600 screen per person

    //gets ship coordinates, later will compare to other things

}
// Use some kind of time system to enlarge a circular object and then find out which objects 
// are touching it and blip them on the screen
// deal with zoom by having variables that change the size of the background
