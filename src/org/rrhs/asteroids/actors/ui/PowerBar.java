package org.rrhs.asteroids.actors.ui;

import mayflower.Actor;
import mayflower.Color;
import mayflower.Label;
import mayflower.MayflowerImage;
import org.rrhs.asteroids.util.MayflowerUtils;

/**
 * Clientside.<br>
 * UI element used in {@link org.rrhs.asteroids.views.EngineerView EngineerView}
 * to draw the power bars on screen.
 */
public class PowerBar extends Actor
{
    private static final int DEFAULT_WIDTH = 80;
    private static final int DEFAULT_HEIGHT = 300;
    private String label = "";
    private static int index = 0; // Unique index
    private Label x = new Label("Can you see this?");

    public PowerBar()
    {
        this.setImage(MayflowerUtils.imageFromColor(DEFAULT_WIDTH,
                DEFAULT_HEIGHT,
                MayflowerUtils.colorFromHsb((45 * index) % 360, 0.66f, 0.8f)));
        index++;
    }

    public void setLabel(String var)
    {
        label = var;
    }

    public void setPercentage(final int percentage)
    {
        if (percentage <= 0) getImage().setTransparency(100);
        else getImage().setTransparency(0);

        int actualPct = MayflowerUtils.clamp(percentage, 1, getHeight());
        int tHeight = (int) ((DEFAULT_HEIGHT / 80.0) * actualPct);
        int deltaY = getHeight() - tHeight;

        MayflowerImage scaled = new MayflowerImage(getImage());
        scaled.scale(DEFAULT_WIDTH, tHeight);
        setImage(scaled);
        setLocation(getX(), getY() + deltaY);
    }

    @Override
    public void act()
    {

        x.setText(label);
        x.setColor(new Color(255, 255, 255));
        x.act();
        x.scale(200, 200);

    }
}
