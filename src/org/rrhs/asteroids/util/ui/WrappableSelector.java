package org.rrhs.asteroids.util.ui;

/**
 * A generic selector that "wraps around"
 * to the min/max value when out of bounds.
 */
public class WrappableSelector
{
    private final int min;
    private final int max;
    private int selector;

    public WrappableSelector(final int min, final int max)
    {
        this.selector = min;
        this.min = min;
        this.max = max;
    }

    /**
     * Increment the selector.<br>
     * If selector would be greater than max, the selector wraps.
     */
    public void increment()
    {
        selector++;
        wrap();
    }

    /**
     * Decrement the selector.<br>
     * If selector would be less than min, the selector wraps.
     */
    public void decrement()
    {
        selector--;
        wrap();
    }

    /**
     * Get the currently selected value.
     */
    public int get()
    {
        return selector;
    }

    /**
     * Wrap the value if necessary.
     */
    private void wrap()
    {
        if (selector < min) selector = max;
        if (selector > max) selector = min;
    }
}
