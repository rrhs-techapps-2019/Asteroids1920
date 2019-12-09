package org.rrhs.asteroids.actors.ui;

import mayflower.Actor;
import mayflower.MayflowerImage;
import org.rrhs.asteroids.util.MayflowerUtils;

/**
 * Clientside.<br>
 * UI element used in {@link org.rrhs.asteroids.views.EngineerView EngineerView}
 * to draw the power bars on screen.
 */
public class PowerBar extends Actor
{
    private static final int DEFAULT_WIDTH = 24;
    private static final int DEFAULT_HEIGHT = 256;

    private static int index = 0; // Unique index

    public PowerBar()
    {
        this.setImage(MayflowerUtils.imageFromColor(DEFAULT_WIDTH,
                DEFAULT_HEIGHT,
                MayflowerUtils.colorFromHsb((45 * index) % 360, 0.66f, 0.8f)));
        index++;
    }

    public void setPercentage(final int percentage)
    {
        int tHeight = (int) ((DEFAULT_HEIGHT / 100.0) * percentage);
        int deltaY = getHeight() - tHeight;

        MayflowerImage scaled = new MayflowerImage(getImage());
        scaled.scale(DEFAULT_WIDTH, tHeight);
        setImage(scaled);
        setLocation(getX(), getY() + deltaY);
    }

    @Override
    public void act()
    {

    }
}
