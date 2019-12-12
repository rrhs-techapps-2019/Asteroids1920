package org.rrhs.asteroids.views;

import mayflower.Color;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerOffline;
import org.rrhs.asteroids.actors.data.ShipData;
import org.rrhs.asteroids.actors.ui.PowerBar;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.util.MayflowerUtils;
import org.rrhs.asteroids.util.ui.WrappableSelector;

import java.awt.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mayflower.Keyboard.*;

public final class EngineerView extends GameView
{
    private static final String FONT_NAME_DEFAULT = "Segoe UI";
    private static final int FONT_SIZE_DEFAULT = 18;

    private final Map<System, PowerBar> barMap;
    private final Map<PowerBar, Point> textPositions = new HashMap<>();
    private final PowerAllocator allocator = new PowerAllocator();
    private final WrappableSelector selector = new WrappableSelector(1, System.values().length - 1);

    public EngineerView(Client client, GameState state)
    {
        super(client, state);

        // Set background color and default font
        MayflowerUtils.setBackgroundColor(this, Color.BLACK);
        this.setFont(FONT_NAME_DEFAULT, FONT_SIZE_DEFAULT);

        // Initialize PowerBar list
        final Iterator<System> iSystems = Arrays.stream(System.values()).iterator();
        barMap = Stream.generate(PowerBar::new)
                .limit(System.values().length)
                .collect(Collectors.toMap(__ -> iSystems.next(), bar -> bar));

        // Add power bars to world
        final int padding = 60;                                                 // Padding from edge
        final int spacing = ((getWidth() + 10) - (2 * padding)) / barMap.size();  // Spacing between power bars
        final AtomicInteger i = new AtomicInteger(0);                // Current iteration index
        Arrays.stream(System.values()).forEach(system -> {
            // Bar
            PowerBar bar = barMap.get(system);
            int xOffset = padding + (spacing * (i.getAndIncrement()));
            this.addObject(bar, xOffset, (2 * padding));

            // Initial label positions
            textPositions.put(bar, new Point(xOffset, (3 * padding) + bar.getHeight()));
        });

        updatePercentages();
        updateSelected();
    }

    protected void update()
    {
        processInput();

        final ShipData data = new ShipData(allocator.getCurrent(System.PILOT),
                allocator.getCurrent(System.WEAPONS),
                allocator.getCurrent(System.SENSORS));
        // TODO: actual networking code -- I guess we need to merge networking changes?
    }

    private void processInput()
    {
        /// Power bar interactions
        // Move selector left
        if (MayflowerUtils.wasKeyPressed(KEY_LEFT))
        {
            selector.decrement();
            updateSelected();
        }

        // Move selector right
        if (MayflowerUtils.wasKeyPressed(KEY_RIGHT))
        {
            selector.increment();
            updateSelected();
        }

        // Increase selection
        if (MayflowerUtils.wasKeyPressed(KEY_UP))
        {
            final System system = System.atIndex(selector.get());
            allocator.allocate(system, 10);
            updatePercentages();
        }

        // Decrease selection
        if (MayflowerUtils.wasKeyPressed(KEY_DOWN))
        {
            final System system = System.atIndex(selector.get());
            allocator.deallocate(system, 10);
            updatePercentages();
        }
    }

    private void updateSelected()
    {
        barMap.values().forEach(PowerBar::deselect);
        final System system = System.atIndex(selector.get());     // Currently selected system
        final PowerBar bar = barMap.get(system);                  // Currently selected bar
        bar.select();
        updateLabels();
    }

    private void updatePercentages()
    {
        for (System system : System.values())
        {
            barMap.get(system).setPercentage(allocator.getCurrent(system));
        }
        updateLabels();
    }

    private void updateLabels()
    {
        barMap.keySet().forEach(system -> {
            // Get parent bar
            final PowerBar bar = barMap.get(system);

            // Get text position and remove old text
            final Point labelPos = textPositions.get(bar);
            this.removeText(labelPos.x, labelPos.y);

            // Update text
            final String text = system.name + " (" + allocator.getCurrent(system) + "%)";
            this.showText(text, labelPos.x, labelPos.y, bar.getColor());
        });
    }

    /**
     * One of three systems that power can be allocated to.<br>
     * Note that while reserve power is considered a system internally,
     * it cannot be used in PowerAllocator methods.
     */
    public enum System
    {
        RESERVE(0),
        PILOT(1),
        WEAPONS(2),
        SENSORS(3);

        private final int index;
        private final String name;

        System(final int index)
        {
            this.index = index;
            this.name = name().charAt(0) + name().substring(1).toLowerCase();
        }

        public static System atIndex(int index)
        {
            Optional<System> opt = Arrays.stream(System.values())
                    .filter(system -> system.index == index)
                    .limit(1)
                    .findFirst();
            if (opt.isPresent()) return opt.get();
            else throw new IllegalArgumentException("No System with the specified index exists.");
        }
    }

    /**
     * A utility class used for managing power
     * allocations between Systems.
     */
    private class PowerAllocator
    {
        private Map<System, Integer> allocations = new EnumMap<>(System.class);

        public PowerAllocator()
        {
            // Default allocation scheme is 100% divided
            // between all systems with remainder in reserve
            for (System system : System.values())
            {
                if (system == System.RESERVE) allocations.put(system, 100 % (System.values().length - 1));
                else allocations.put(system, 100 / (System.values().length - 1));
            }
        }

        /**
         * Allocate some reserve power to a system.<br>
         * If the amount is greater than the current reserve power, all of
         * the power available in reserve will be allocated to the system.
         *
         * @param system System to allocate power to. Cannot be {@link System#RESERVE RESERVE}.
         * @param amount Amount of power to allocate
         */
        private void allocate(final System system, final int amount)
        {
            if (system == System.RESERVE) throw new IllegalArgumentException();
            final int current = allocations.get(system);
            final int delta = MayflowerUtils.clamp(amount, 0, allocations.get(System.RESERVE));
            final int vNew = current + delta;
            final int rNew = allocations.get(System.RESERVE) - delta;

            allocations.put(system, vNew);
            allocations.put(System.RESERVE, rNew);
            barMap.get(system).setPercentage(vNew);
        }

        /**
         * Deallocate some power from a system.<br>
         * If the amount is greater than the current system power, all of
         * the power in the system will be deallocated.
         *
         * @param system System to return power from. Cannot be {@link System#RESERVE RESERVE}.
         * @param amount Amount of power to return
         */
        private void deallocate(final System system, final int amount)
        {
            if (system == System.RESERVE) throw new IllegalArgumentException();
            final int current = allocations.get(system);
            final int delta = MayflowerUtils.clamp(amount, 0, current);
            final int vNew = current - delta;
            final int rNew = allocations.get(System.RESERVE) + delta;

            allocations.put(system, vNew);
            allocations.put(System.RESERVE, rNew);
            barMap.get(system).setPercentage(vNew);
        }

        /**
         * Set the power currently allocated to a system.<br>
         * If there is not enough reserve power available for the desired value,
         * all of the power available in reserve will be allocated to the system.
         *
         * @param system System to adjust. Cannot be {@link System#RESERVE RESERVE}.
         * @param value  Percent power to set
         */
        private void set(final System system, final int value)
        {
            if (system == System.RESERVE) throw new IllegalArgumentException();
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
    }

    /**
     * Offline test
     */
    public static void main(String[] args)
    {
        new RunnerOffline(EngineerView.class);
    }
}