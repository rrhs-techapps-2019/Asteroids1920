package org.rrhs.asteroids.actors;

import mayflower.Actor;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NetworkActor extends Actor
{
    private int id;
    private int speed;
    private int rotationSpeed;
    private int direction;
    private String type;
    private Map<Integer, Point2D> locationHistory;
    private int tick;

    public NetworkActor(int id, String type)
    {
        this.id = id;
        this.type = type;
        locationHistory = new HashMap<>();
        tick = 0;
    }

    public int getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public int getRotationSpeed()
    {
        return rotationSpeed;
    }

    public void setRotationSpeed(int speed)
    {
        this.rotationSpeed = speed;
    }

    public void move()
    {
        move(speed);
    }

    public void act()
    {
        turn(getRotationSpeed());
        move();
        ++tick;
    }

    public String toString()
    {
        Map<String, String> data = new HashMap<>();
        data.put("id", "" + id);
        data.put("x", "" + getX());
        data.put("y", "" + getY());
        data.put("rotation", "" + getRotation());
        data.put("speed", "" + getSpeed());
        data.put("rotspeed", "" + getRotationSpeed());
        return data.toString();
    }

    public void setLocation(double x, double y)
    {
        super.setLocation(x, y);
        locationHistory.put(tick, new Point(x, y));
        while (locationHistory.size() > 50)
        {
            locationHistory.remove(getSmallest());
        }
    }

    private int getSmallest()
    {
        Set<Integer> s = locationHistory.keySet();
        Integer[] arr = s.toArray(new Integer[0]);
        Arrays.sort(arr);
        return arr[0];
    }

    class Point extends Point2D
    {
        double x;
        double y;

        Point(double x, double y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public double getX()
        {
            return x;
        }

        @Override
        public double getY()
        {
            return y;
        }

        @Override
        public void setLocation(double x, double y)
        {
            this.x = x;
            this.y = y;
        }
    }
}