package org.rrhs.asteroids.network;

import org.rrhs.asteroids.util.logging.Logger;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.Map;

public class ActionManager<T>
{
    private final Map<PacketAction, Class<? extends T>> actionMap = new EnumMap<>(PacketAction.class);

    public void put(PacketAction action, Class<? extends T> target)
    {
        actionMap.put(action, target);
    }

    public T get(String action)
    {
        return get(PacketAction.fromString(action));
    }

    public T get(PacketAction action)
    {
        Class<? extends T> target = actionMap.get(action);
        if (target == null)
        {
            Logger.error("No mapping exists for PacketAction." + action + "!");
            return null;
        }

        return makeInstance(actionMap.get(action));
    }

    private T makeInstance(Class<? extends T> target)
    {
        T actionInstance = null;
        try
        {
            Constructor<? extends T> constructor = target.getConstructor();
            actionInstance = constructor.newInstance();
        }
        catch (NoSuchMethodException e)
        {
            Logger.error("Action class " + target.getSimpleName() + " does not have a valid noarg constructor");
        }
        catch (ReflectiveOperationException e)
        {
            Logger.error("Error while instantiating " + target.getSimpleName() + ": " + e);
        }
        return actionInstance;
    }
}