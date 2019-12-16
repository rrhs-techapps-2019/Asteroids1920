package org.rrhs.asteroids.actors.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Dumb, generic data map.
 *
 * @param <K> Map key type
 * @param <V> Map value type
 */
public class Data<K, V>
{
    private final Map<K, V> map = new HashMap<>();

    protected void put(K key, V value)
    {
        map.put(key, value);
    }

    protected void get(K key)
    {
        map.get(key);
    }

    @Override
    public String toString()
    {
        return map.toString();
    }
}
