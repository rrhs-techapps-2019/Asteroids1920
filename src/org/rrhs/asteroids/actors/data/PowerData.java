package org.rrhs.asteroids.actors.data;

import org.rrhs.asteroids.System;

import java.util.Map;

public class PowerData extends Data<System, Integer>
{
    public PowerData(Map<System, Integer> allocatorMap)
    {
        this(allocatorMap.get(System.PILOT),
                allocatorMap.get(System.WEAPONS),
                allocatorMap.get(System.SENSORS));
    }

    public PowerData(int pilot, int weapons, int sensors)
    {
        super.put(System.PILOT, pilot);
        super.put(System.WEAPONS, weapons);
        super.put(System.SENSORS, sensors);
    }
}