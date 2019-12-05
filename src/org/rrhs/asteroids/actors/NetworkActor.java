package org.rrhs.asteroids.actors;

import mayflower.Actor;

import java.util.HashMap;
import java.util.Map;

public class NetworkActor extends Actor
{
    private int id;
    private int speed;
    private int rotationSpeed;
    private int direction;
    private String type;

    public NetworkActor(int id, String type)
    {
        this.id = id;
        this.type = type;
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
    }

    public String toString() {
        Map<String, String> data = new HashMap<>();
        data.put("id", "" + id);
        data.put("x", "" + getX());
        data.put("y", "" + getY());
        data.put("rotation", "" + getRotation());
        data.put("speed", "" + getSpeed());
        data.put("rotspeed", "" + getRotationSpeed());
        return data.toString();
    }

}