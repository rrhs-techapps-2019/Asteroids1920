package org.rrhs.asteroids.actors.objects;

import mayflower.Actor;
import mayflower.MayflowerImage;

/**
 * Write a description of class Circle here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Circle extends Actor
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Circle
     */
    public Circle()
    {
        MayflowerImage img = new MayflowerImage("img/Circle.png");
        img.scale(40, 40);
        setImage(img);
    }

    @Override
    public void act()
    {
    }
}
