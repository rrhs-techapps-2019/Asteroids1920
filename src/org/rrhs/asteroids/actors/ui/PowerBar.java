package org.rrhs.asteroids.actors.ui;

import mayflower.Actor;
import mayflower.Color;
import mayflower.MayflowerImage;
import org.rrhs.asteroids.util.MayflowerUtils;

/**
 * Clientside.<br>
 * UI element used in {@link org.rrhs.asteroids.views.EngineerView EngineerView}
 * to draw the power bars on screen.
 */
public class PowerBar extends Actor
{
    public static final int DEFAULT_WIDTH = 80;
    public static final int DEFAULT_HEIGHT = 300;
    private static final Color COLOR_SELECTED = new Color(200, 200, 200);
    private static final Color COLOR_NOT_SELECTED = new Color(230,230,230);

    private static int index = 0; // Unique index
    private final Color colorDefault;
    private Color colorCurrent;

    public PowerBar()
    {
        this.colorDefault = MayflowerUtils.colorFromHsb((60 * (index++)) % 360, 0.66f, 0.8f);
        this.colorCurrent = colorDefault;
        updateImage();
    }

    public void setPercentage(final int percentage)
    {
        int actualPct = MayflowerUtils.clamp(percentage, 1, getHeight());
        int tHeight = (int) ((DEFAULT_HEIGHT / 80.0) * actualPct);
        int deltaY = getHeight() - tHeight;

        MayflowerImage scaled = new MayflowerImage(getImage());
        scaled.scale(DEFAULT_WIDTH, tHeight);
        setImage(scaled);
        setLocation(getX(), getY() + deltaY);
    }

    public void select()
    {
        setSelected(true);
    }

    public void deselect()
    {
        setSelected(false);
    }

    private void setSelected(boolean select)
    {
        if (select) this.colorCurrent = colorDefault;
        else this.colorCurrent = COLOR_NOT_SELECTED;
        updateImage();
    }

    public Color getColor()
    {
        return colorCurrent;
    }

    private void updateImage()
    {
        final int height = (getHeight() > 0) ? getHeight() : DEFAULT_HEIGHT;
        this.setImage(MayflowerUtils.imageFromColor(DEFAULT_WIDTH,
                height,
                colorCurrent));
    }

    @Override
    public void act()
    {
    }
}
