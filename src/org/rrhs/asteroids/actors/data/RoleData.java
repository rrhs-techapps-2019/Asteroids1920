package org.rrhs.asteroids.actors.data;

import org.rrhs.asteroids.Role;
import org.rrhs.asteroids.System;

import java.util.Map;

public class RoleData extends Data<Role, Boolean> {
    public RoleData() {
        super.put(Role.ENGINEER, true);
        super.put(Role.PILOT, true);
        super.put(Role.WEAPONS, true);
        super.put(Role.SENSORS, true);
    }

    public RoleData(Map<Role, Boolean> data) {
        super.putAll(data);
    }

    public Boolean isAvailable(Role role) {
        return super.get(role);
    }

    public void claim(Role role) {
        super.put(role, false);
    }
}
