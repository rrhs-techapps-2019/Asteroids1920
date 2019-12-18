package org.rrhs.asteroids.network;

import java.util.Arrays;

public enum PacketAction
{
    IDENTIFY,
    ADD,
    UPDATE,
    MOVE,
    STOP,
    TURN,
    STOP_TURN,
    POWER,
    CLAIM_ROLE,
    RELEASE_ROLE,
    UPDATE_ROLES;

    public static PacketAction fromString(String action)
    {
        return Arrays.stream(PacketAction.values())
                .filter(pAction -> action.equals(pAction.name()))
                .findFirst()
                .orElse(null);
    }
}
