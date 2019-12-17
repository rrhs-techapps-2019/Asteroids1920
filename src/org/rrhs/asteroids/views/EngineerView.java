package org.rrhs.asteroids.views;

import mayflower.Color;
import mayflower.Mayflower;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerClient;
import org.rrhs.asteroids.System;
import org.rrhs.asteroids.actors.data.PowerData;
import org.rrhs.asteroids.actors.ui.PowerBar;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.network.Packet;
import org.rrhs.asteroids.network.PacketAction;
import org.rrhs.asteroids.util.MayflowerUtils;
import org.rrhs.asteroids.util.logging.LogLevel;
import org.rrhs.asteroids.util.logging.Logger;
import org.rrhs.asteroids.util.logging.LoggerConfigurator;
import org.rrhs.asteroids.util.logging.writers.ConsoleWriter;
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
        this.showReserveError(false);


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
    }

    private void processInput()
    {
        /// Power bar interactions
        // Increase selection
        if (Mayflower.isKeyDown(KEY_UP))
        {
            this.showReserveError(false);
            final System system = System.atIndex(selector.get());
            if (!allocator.allocate(system, 1)) this.showReserveError(true);
            updatePercentages();

            return;
        } else if (MayflowerUtils.wasKeyReleased(KEY_UP))
        {
            updateNetwork();
        }

        // Decrease selection
        if (Mayflower.isKeyDown(KEY_DOWN))
        {
            this.showReserveError(false);
            final System system = System.atIndex(selector.get());
            if (!allocator.deallocate(system, 1)) this.showReserveError(true);
            updatePercentages();

            return;
        } else if (MayflowerUtils.wasKeyReleased(KEY_DOWN))
        {
            updateNetwork();
        }

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
    }

    private void flashBackground()
    {
        new Thread(() -> {
            try
            {
                MayflowerUtils.setBackgroundColor(this, new Color(80, 80, 80));
                Thread.sleep(50L);
                MayflowerUtils.setBackgroundColor(this, Color.BLACK);
                this.showReserveError(true);

            }
            catch (InterruptedException e)
            {
                Logger.error(e + ": Warning flash thread was interrupted");
            }
        }).start();


    }

    //Displays error if an illegal power allocation takes place
    private void showReserveError(boolean bool)
    {
        if (bool)
        {
            this.showText("Not enough reserve power!", 270, 25, new Color(255, 255, 255));
        } else
        {
            this.showText("Not enough reserve power!", 270, 25, new Color(0, 0, 0));
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
            final String text = system.getName() + " (" + allocator.getCurrent(system) + "%)";
            this.showText(text, labelPos.x, labelPos.y, bar.getColor());
        });
    }

    private void updateNetwork()
    {
        PowerData data = new PowerData(allocator.getAllocations());
        Packet packet = new Packet(PacketAction.POWER, data);
        client.send(packet);
    }

    /**
     * A utility class used for managing power
     * allocations between Systems.
     */
    private class PowerAllocator
    {
        private static final int DEFAULT_RESERVE = 10;
        private Map<System, Integer> allocations = new EnumMap<>(System.class);

        public PowerAllocator()
        {
            for (System system : System.values())
            {
                if (system == System.RESERVE) allocations.put(system, DEFAULT_RESERVE);
                else allocations.put(system, (100 - DEFAULT_RESERVE) / (System.values().length - 1));
            }
        }

        /**
         * Allocate some reserve power to a system.<br>
         * If the amount is greater than the current reserve power, all of
         * the power available in reserve will be allocated to the system.
         *
         * @param system System to allocate power to. Cannot be {@link System#RESERVE RESERVE}.
         * @param amount Amount of power to allocate
         * @return true if the full amount was allocated, otherwise false
         */
        private boolean allocate(final System system, final int amount)
        {
            boolean r = true;
            if (system == System.RESERVE) throw new IllegalArgumentException();
            final int current = allocations.get(system);
            final int delta = MayflowerUtils.clamp(amount, 0, allocations.get(System.RESERVE));
            final int vNew = current + delta;
            final int rNew = allocations.get(System.RESERVE) - delta;
            if (delta < amount) r = false;

            allocations.put(system, vNew);
            allocations.put(System.RESERVE, rNew);
            barMap.get(system).setPercentage(vNew);
            return r;
        }

        /**
         * Deallocate some power from a system.<br>
         * If the amount is greater than the current system power, all of
         * the power in the system will be deallocated.
         *
         * @param system System to return power from. Cannot be {@link System#RESERVE RESERVE}.
         * @param amount Amount of power to return
         * @return true if the full amount was deallocated, otherwise false
         */
        private boolean deallocate(final System system, final int amount)
        {
            boolean r = true;
            if (system == System.RESERVE) throw new IllegalArgumentException();
            final int current = allocations.get(system);
            final int delta = MayflowerUtils.clamp(amount, 0, current);
            final int vNew = current - delta;
            final int rNew = allocations.get(System.RESERVE) + delta;
            if (delta < amount) r = false;

            allocations.put(system, vNew);
            allocations.put(System.RESERVE, rNew);
            barMap.get(system).setPercentage(vNew);
            return r;
        }

        /**
         * Get the amount of power currently allocated to a {@link System}.
         */
        private int getCurrent(final System system)
        {
            return allocations.get(system);
        }

        public Map<System, Integer> getAllocations()
        {
            return Collections.unmodifiableMap(allocations);
        }
    }

    public static PowerData getDefaultPowerState()
    {
        int defaultAllocation = (100 - PowerAllocator.DEFAULT_RESERVE) / (System.values().length - 1);
        return new PowerData(defaultAllocation, defaultAllocation, defaultAllocation);
    }

    public static void main(String[] args)
    {
        LoggerConfigurator.empty()
                .addWriter(new ConsoleWriter(java.lang.System.out))
                .level(LogLevel.DEBUG)
                .activate();
        new RunnerClient(EngineerView.class);
    }
}