package org.rrhs.asteroids.views;

import mayflower.Color;
import mayflower.Keyboard;
import mayflower.World;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerOffline;
import org.rrhs.asteroids.actors.ui.PowerBar;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.util.MayflowerUtils;
import org.rrhs.asteroids.util.NetworkUtils.Message;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EngineerView extends World
{
    private final Client client;
    private final GameState state;
    private final List<PowerBar> bars;
    private final PowerAllocator allocator = new PowerAllocator();

    private int resyncCounter = 0;  // Number of frames since last refresh
    private int selected = 0;       // Currently selected column

    public EngineerView(Client client, GameState state)
    {
        this.client = client;
        this.state = state;

        // Set background color
        MayflowerUtils.setBackgroundColor(this, Color.BLACK);

        // Initialize PowerBar list
        bars = Stream.generate(PowerBar::new)
                .limit(4L)
                .collect(Collectors.toList());

        // Add power bars to world
        final int padding = 32;
        final int delta = (getWidth() - (2 * padding)) / bars.size();
        for (int i = 0; i < bars.size(); i++)
        {
            int xOffset = padding + (delta * i);
            this.addObject(bars.get(i), xOffset, padding);
        }
    }

    @Override
    public void act()
    {
        sync();
        processInput();
        draw();
    }

    private void sync()
    {
        resyncCounter++;
        if (resyncCounter > 30)
        {
            client.send(Message.UPDATE);
        }
    }

    private void processInput()
    {
        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_RIGHT))
        {

        }

        if (MayflowerUtils.wasKeyPressed(Keyboard.KEY_LEFT))
        {

        }
    }

    private void draw()
    {

    }

    /**
     * Get the amount of power currently allocated to a {@link System}.
     */
    public int getCurrentPower(final System system)
    {
        return allocator.getCurrent(system);
    }

    /**
     * One of three systems that power can be allocated to.
     */
    public enum System
    {
        PILOT,
        WEAPONS,
        SENSORS
    }

    private static class PowerAllocator
    {
        private int reserve = 100;
        private Map<System, Integer> allocations = new EnumMap<>(System.class);

        public PowerAllocator()
        {
            for (System system : System.values())
            {
                allocations.put(system, 0);
            }
        }

        /**
         * Allocate some reserve power to a system.<br>
         * If the amount is greater than the current reserve power, all of
         * the power available in reserve will be allocated to the system.
         *
         * @param system System to allocate power to
         * @param amount Amount of power to allocate
         */
        private void allocate(final System system, final int amount)
        {
            final int current = allocations.get(system);
            final int delta = clamp(amount, 0, reserve);

            allocations.put(system, current + delta);
            reserve -= delta;
        }

        /**
         * Deallocate some power from a system.<br>
         * If the amount is greater than the current system power, all of
         * the power in the system will be deallocated.
         *
         * @param system System to return power from
         * @param amount Amount of power to return
         */
        private void deallocate(final System system, final int amount)
        {
            final int current = allocations.get(system);
            final int delta = clamp(amount, 0, current);

            allocations.put(system, current - delta);
            reserve += delta;
        }

        /**
         * Set the power currently allocated to a system.<br>
         * If there is not enough reserve power available for the desired value,
         * all of the power available in reserve will be allocated to the system.
         *
         * @param system System to adjust
         * @param value  Percent power to set
         */
        private void set(final System system, final int value)
        {
            final int diff = (value - allocations.get(system));
            allocate(system, diff);
        }

        /**
         * Get the amount of power currently allocated to a {@link System}.
         */
        private int getCurrent(final System system)
        {
            return allocations.get(system);
        }

        /**
         * Clamp a value to a range.
         *
         * @param value Value to clamp
         * @param min   Minimum return value
         * @param max   Maximum return value
         * @return If value < min, then min; if value > max, then max; otherwise value
         */
        private int clamp(final int value, final int min, final int max)
        {
            return Math.max(min, Math.min(max, value));
        }
    }

    /**
     * Offline test
     */
    public static void main(String[] args)
    {
        new RunnerOffline(EngineerView.class);
    }
}