package org.rrhs.asteroids.util;

import mayflower.Color;
import mayflower.Mayflower;
import mayflower.MayflowerImage;
import mayflower.World;

public final class MayflowerUtils
{
    /**
     * Check if a key was pressed this frame.
     *
     * @param keyCode The key's keycode
     * @see mayflower.Keyboard Keyboard
     */
    public static boolean wasKeyPressed(int keyCode)
    {
        return Mayflower.isKeyDown(keyCode) && !Mayflower.wasKeyDown(keyCode);
    }

    /**
     * Check if a key was released this frame.
     *
     * @param keyCode The key's keycode
     * @see mayflower.Keyboard Keyboard
     */
    public static boolean wasKeyReleased(int keyCode)
    {
        return !Mayflower.isKeyDown(keyCode) && Mayflower.wasKeyDown(keyCode);
    }

    /**
     * Generate a Mayflower color using the HSB color model.
     *
     * @param h Hue component (0.0 to 360.0)
     * @param s Saturation of the color (0.0 to 1.0)
     * @param b Brightness of the color (0.0 to 1.0)
     * @return a Color with the specified hue, saturation, and brightness
     * @see <a href="https://en.wikipedia.org/wiki/HSL_and_HSV">HSL and HSV</a>
     */
    public static Color colorFromHsb(float h, float s, float b)
    {
        final float hAbs = h / 360f;
        java.awt.Color targetColor = java.awt.Color.getHSBColor(hAbs, s, b);
        return new Color(targetColor);
    }

    /**
     * Generate a solid-color Mayflower image with the specified width, height, and color.
     *
     * @param width  Image width
     * @param height Image height
     * @param color  Image color
     */
    public static MayflowerImage imageFromColor(final int width, final int height, final Color color)
    {
        return new MayflowerImage(width, height, color);
    }

    /**
     * Set the background color of a {@link World}.
     *
     * @param world Target world
     * @param color Desired color
     */
    public static void setBackgroundColor(World world, Color color)
    {
        world.setBackground(imageFromColor(world.getWidth(), world.getHeight(), color));
    }
}
