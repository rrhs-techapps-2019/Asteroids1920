package org.rrhs.asteroids.network;

import org.rrhs.asteroids.actors.NetworkActor;
import org.rrhs.asteroids.actors.data.Data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packet
{
    private Map<String, String> fields = new LinkedHashMap<>();

    /**
     * Construct a Packet with the specified action and actor data
     */
    public Packet(PacketAction action, NetworkActor actor)
    {
        populateFields(action, actor.toString(), actor.getType());
    }

    public Packet(PacketAction action, Data<?, ?> data)
    {
        populateFields(action, data.toString(), null);
    }

    /**
     * Construct a Packet with the specified action
     */
    public Packet(PacketAction action)
    {
        populateFields(action, null, null);
    }

    /**
     * Construct a Packet with the specified action and type
     */
    public Packet(PacketAction action, String type)
    {
        populateFields(action, null, type);
    }

    /**
     * Reconstruct a Packet from a raw network message
     */
    public Packet(String message)
    {
        this.fields = Stream.of(message.split(":", 3))
                .map(pair -> pair.split("=", 2))
                .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
    }

    public String getData()
    {
        return fields.get("data");
    }

    public String getAction()
    {
        return fields.get("action");
    }

    public String getType()
    {
        return fields.get("type");
    }

    public String toString()
    {
        return fields.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(":"));
    }

    private void populateFields(PacketAction action, String data, String type)
    {
        fields.put("action", action.toString());
        fields.put("data", data);
        fields.put("type", type);
    }
}
