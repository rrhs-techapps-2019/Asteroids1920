package org.rrhs.asteroids;

import mayflower.Mayflower;
import mayflower.MayflowerHeadless;
import mayflower.World;
import org.rrhs.asteroids.network.Server;
import org.rrhs.asteroids.util.logging.Logger;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Runner
{
    protected final Class<? extends World> targetWorld;
    private Mayflower mayflower;

    public Runner(final Class<? extends World> targetWorld, boolean headless)
    {
        this.targetWorld = targetWorld;

        if (headless)
        {
            this.mayflower = new MayflowerHeadless("Asteroids", 800, 600)
            {
                @Override
                public void init()
                {
                    Runner.this.init(headless);
                }
            };
        } else
        {
            this.mayflower = new Mayflower("Asteroids", 800, 600)
            {
                @Override
                public void init()
                {
                    Runner.this.init(headless);
                }
            };
        }
    }

    public static void main(String[] args)
    {
    }

    protected void init(boolean headless)
    {
        Logger.info("Mayflower initialized successfully. (headless=" + headless + ")");
    }

    protected void initClient(final Object... args)
    {
        World world = tryCreateWorld(targetWorld, args);
        Mayflower.setWorld(world);
    }

    protected void initServer()
    {
        World world = tryCreateWorld(targetWorld);
        new Server(world);
        Mayflower.setWorld(world);
    }

    protected World tryCreateWorld(Class<? extends World> worldClass, Object... args)
    {
        try
        {
            // getConstructor() only accepts explicit types, meaning that it fails
            // to locate a constructor when we receive a subclass of any parameter.
            // Because of this, it's necessary to do parameter type checking manually.
            Constructor<?> constructor = null;
            for (Constructor<?> candidate : worldClass.getConstructors())
            {
                Class<?>[] parameterTypes = candidate.getParameterTypes();
                if (args.length != parameterTypes.length) continue;

                // Constructor is valid if both parameters and arguments are noargs
                if (args.length == 0)
                {
                    constructor = candidate;
                    break;
                }

                // Zip parameter and argument types to pairs
                Map<Class<?>, Class<?>> map = IntStream.range(0, parameterTypes.length).boxed()
                        .collect(Collectors.toMap(i -> parameterTypes[i], i -> args[i].getClass()));

                // Make sure all parameter types match argument types
                if (map.entrySet().stream()
                        .noneMatch(entry -> entry.getKey().isAssignableFrom(entry.getValue())))
                {
                    continue;
                }
                constructor = candidate;
            }

            if (constructor == null)
                throw new IllegalArgumentException("Could not find a constructor for the specified world that accepts the given arguments");
            return (World) constructor.newInstance(args);
        }
        catch (Exception e)
        {
            Logger.error("Could not initialize instance of " + worldClass.getName() + ": " + e);
        }
        return null;
    }
}