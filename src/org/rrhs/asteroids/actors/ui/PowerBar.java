package org.rrhs.asteroids.actors.ui;

import mayflower.Actor;
import org.rrhs.asteroids.util.MayflowerUtils;

/**
 * Clientside.<br>
 * UI element used in {@link org.rrhs.asteroids.views.EngineerView EngineerView}
 * to draw the power bars on screen.
 */
public class PowerBar extends Actor
{
    private static int index = 0; // Unique index

    public PowerBar()
    {
        this.setImage(MayflowerUtils.imageFromColor(24,
                256,
                MayflowerUtils.colorFromHsb((45 * index) % 360, 0.66f, 0.8f)));
        index++;
    }

    public void setPercentage(final int percentage)
    {

    }

    @Override
    public void act()
    {

    }
}
