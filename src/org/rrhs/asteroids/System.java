package org.rrhs.asteroids;

import java.util.Arrays;
import java.util.Optional;

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

    public String getName()
    {
        return name;
    }
}
