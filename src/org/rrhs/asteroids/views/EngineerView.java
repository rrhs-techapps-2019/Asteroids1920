package org.rrhs.asteroids.views;

import mayflower.Actor;
import mayflower.Color;
import mayflower.Mayflower;
import org.rrhs.asteroids.GameState;
import org.rrhs.asteroids.RunnerOffline;
import org.rrhs.asteroids.actors.objects.Selector;
import org.rrhs.asteroids.actors.ui.PowerBar;
import org.rrhs.asteroids.network.Client;
import org.rrhs.asteroids.util.MayflowerUtils;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mayflower.Keyboard.*;

public final class EngineerView extends GameView
{
    private final List<PowerBar> bars;
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

        // Initialize PowerBar list
        bars = Stream.generate(PowerBar::new)
                .limit(4L)
                .collect(Collectors.toList());
        String[] arr = {"Reserve", "Pilot", "Weapons", "Sensors"};

        // Add power bars to world

        final int padding = 32;
        final int delta =  (getWidth()+10 - (2 * padding)) / bars.size();
        for (int i = 0; i < bars.size(); i++)
        {
            int xOffset = padding + (delta * i);
            this.addObject(bars.get(i), xOffset+50, padding);
           // this.showText(arr[i], xOffset+50, padding+350, new Color(102,255,102));
        }
        //x=526 sensors (3)
        //x=341 weapons (2)
        //x=155 pilot (1)
        //x=-35 reserve (0)

        this.showText(arr[0],padding+(delta*0)+24, padding+350,new Color(102,255,102));
        this.showText(arr[1],padding+(delta*1)+55, padding+350,new Color(102,255,102));
        this.showText(arr[2],padding+(delta*2)+22, padding+350,new Color(102,255,102));
        this.showText(arr[3],padding+(delta*3)+27, padding+350,new Color(102,255,102));
        this.addObject(selector,-35,0);
    }

    protected void update()
    {
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