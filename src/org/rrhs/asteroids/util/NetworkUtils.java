package org.rrhs.asteroids.util;

import org.rrhs.asteroids.actors.objects.Asteroid;
import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.objects.Ship;
import org.rrhs.asteroids.util.logging.Logger;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class NetworkUtils
{
    private static final Map<String, Class<? extends NetworkActor>> TYPE_MAP = new HashMap<>();

    static
    {
        // I hate Java 8
        TYPE_MAP.put("ship", Ship.class);
        TYPE_MAP.put("asteroid", Asteroid.class);
    }

    private NetworkUtils()
    {
    }

    public static Map<String, Integer> reconstituteMap(String raw)
    {
        return reconstituteMap(raw, Integer::parseInt);
    }

    public static <V> Map<String, V> reconstituteMap(String raw, Function<String, V> valueGenerator)
    {
        return reconstituteMap(raw, Function.identity(), valueGenerator);
    }

    public static <K, V> Map<K, V> reconstituteMap(String raw, Function<String, K> keyGenerator, Function<String, V> valueGenerator)
    {
        String[] dataRaw = raw.substring(1, raw.length() - 1)
                .replaceAll("\\p{javaSpaceChar}", "")
                .split(",");
        return Arrays.stream(dataRaw)
                .map(field -> field.split("="))
                .collect(Collectors.toMap(a -> keyGenerator.apply(a[0]), a -> valueGenerator.apply(a[1])));
    }

    public static NetworkActor reconstituteActor(String type, Map<String, Integer> data)
    {
        NetworkActor actor = null;

        int id = data.get("id");
        Class<? extends NetworkActor> targetClass = TYPE_MAP.get(type);

        try
        {
            Constructor<? extends NetworkActor> constructor = targetClass.getConstructor(int.class);
            actor = constructor.newInstance(id);
        }
        catch (NullPointerException e)
        {
            Logger.error("Actor type " + type + " isn't mapped in GameState");
            return null;
        }
        catch (NoSuchMethodException e)
        {
            Logger.error("Actor class for type " + type + " doesn't have a valid constructor");
            return null;
        }
        catch (ReflectiveOperationException e)
        {
            Logger.error("Error while instatiating " + targetClass.getName() + " for type " + type + ": " + e.getMessage());
            return null;
        }

        updateActor(actor, data);
        return actor;
    }

    public static void updateActor(NetworkActor actor, Map<String, Integer> data)
    {
        actor.setLocation(data.get("x"), data.get("y"));
        actor.setRotation(data.get("rotation"));
        actor.setSpeed(data.get("speed"));
        actor.setRotationSpeed(data.get("rotspeed"));
    }
}