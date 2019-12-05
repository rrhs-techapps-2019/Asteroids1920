package org.rrhs.asteroids.actors.data;

import org.rrhs.asteroids.Role;

import java.util.Collections;
import java.util.Map;

public class RoleData extends Data<Role, Integer>
{
    private static final int NULL_ID = Integer.MIN_VALUE;

    public RoleData()
    {
        super.put(Role.PILOT, NULL_ID);
        super.put(Role.WEAPONS, NULL_ID);
        super.put(Role.SENSORS, NULL_ID);
        super.put(Role.ENGINEER, NULL_ID);
    }

    public RoleData(Map<Role, Integer> data)
    {
        super.putAll(data);
    }

    public boolean isAvailable(Role role)
    {
        return super.get(role) == NULL_ID;
    }

    public void claim(Role role, int id)
    {
        super.put(role, id);
    }

    public void release(Role role)
    {
        super.put(role, NULL_ID);
    }

    public Map<Role, Integer> getAll()
    {
        return Collections.unmodifiableMap(super.getMap());
    }
}
