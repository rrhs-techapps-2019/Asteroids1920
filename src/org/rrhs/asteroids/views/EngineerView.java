package org.rrhs.asteroids.views;

import mayflower.Actor;
import mayflower.Color;
import mayflower.Mayflower;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerOffline;
import org.rrhs.asteroids.actors.objects.Selector;
import org.rrhs.asteroids.actors.data.ShipData;
import org.rrhs.asteroids.actors.ui.PowerBar;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.util.MayflowerUtils;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mayflower.Keyboard.*;

public final class EngineerView extends GameView
{
    private final Map<System, PowerBar> barMap;
    private final PowerAllocator allocator = new PowerAllocator();
    private Actor selector = new Selector();
    private int selected = 0;  // Currently selected column
    private int[] selectorX = {-35,155,341,526};
    private int indexS = 0;
    public EngineerView(Client client, GameState state)
    {
        super(client, state);

        // Set background color
        MayflowerUtils.setBackgroundColor(this, Color.BLACK);
        this.setFont("Calibri", 24);

        // Initialize PowerBar list
        Iterator<System> iSystems = Arrays.stream(System.values()).iterator();
        barMap = Stream.generate(PowerBar::new)
                .limit(4L)
                .collect(Collectors.toMap(ignore -> iSystems.next(), bar -> bar));

        // Add power bars to world
        final int padding = 50;
        final int delta = (getWidth() + 10 - (2 * padding)) / barMap.size();
        AtomicInteger i = new AtomicInteger(0);
        barMap.forEach((system, bar) -> {
            int xOffset = padding + (delta * (i.getAndIncrement()));
            this.addObject(bar, xOffset + padding, padding);
            this.showText(system.name, xOffset + padding, (2 * padding) + bar.getHeight(), new Color(102, 255, 102));
            bar.setPercentage(allocator.getCurrent(system));
        });

        // Add selector to world
        this.addObject(selector, -35, 0);
    }

    protected void update()
    {
        final ShipData data = new ShipData(allocator.getCurrent(System.PILOT),
                allocator.getCurrent(System.WEAPONS),
                allocator.getCurrent(System.SENSORS));
        // TODO: actual networking code -- I guess we need to merge networking changes?

        if(!Mayflower.wasKeyDown(KEY_LEFT) && Mayflower.isKeyDown(KEY_LEFT)){

            if(indexS == 0){
                indexS = 3;
            }
            else{
                indexS-=1;
            }

            selector.setLocation(selectorX[indexS],0);

        }
        else if(!Mayflower.wasKeyDown(KEY_RIGHT) && Mayflower.isKeyDown(KEY_RIGHT)){
            if(indexS == 3){
                indexS = 0;
            }
            else{
                indexS+=1;
            }
            selector.setLocation(selectorX[indexS],0);
            Mayflower.delay(10);
        }
    }

    /**
     * Get the amount of power currently allocated to a {@link System}.
     */
    public int getCurrentPower(final System system)
    {
        return allocator.getCurrent(system);
    }

    /**
     * One of three systems that power can be allocated to.<br>
     * Note that while reserve power is considered a system internally,
     * it cannot be used in PowerAllocator methods.
     */
    public enum System
    {
        RESERVE,
        PILOT,
        WEAPONS,
        SENSORS;

        private final String name;

        System()
        {
            this.name = name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    private class PowerAllocator
    {
        private int reserve = 1;
        private Map<System, Integer> allocations = new EnumMap<>(System.class);

        public PowerAllocator()
        {
            for (System system : System.values())
            {
                if (system == System.RESERVE) allocations.put(system, 1);
                else allocations.put(system, 33);
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
            final int delta = MayflowerUtils.clamp(amount, 0, reserve);
            final int vNew = current + delta;

            allocations.put(system, vNew);
            barMap.get(system).setPercentage(vNew);
            reserve -= delta;
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

            allocations.put(system, vNew);
            barMap.get(system).setPercentage(vNew);
            reserve += delta;
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