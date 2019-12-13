package org.rrhs.asteroids.network;

import org.rrhs.asteroids.actors.NetworkActor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MessageHandler {
    /**
     * Parser class for processing messages on clients and servers
     *
     * @param message the message send from the client
     * @return a map of the information passed to the recipient
     */
    public Packet parseMessage(String message) {
        System.out.println(message);
        String[] parser = message.split("--==--");
        System.out.println("arraything is " + Arrays.toString(parser));
        if (parser.length == 2)
            return new Packet(parser[0], parser[1]);
        else if (parser.length == 3 && !parser[1].equals("null"))
            return new Packet(parser[0], parser[1], parser[2]);
        else if (parser.length == 3)
            return new Packet(parser[0]);
        return null;
    }

    public NetworkActor actorComprehension(String message) {
        HashMap<String, String> ret = new HashMap<>();
        System.out.println("message1: " + message);
        message = message.substring(1, message.length() - 1);
        System.out.println("message is: " + message);
        String[] pairs = message.split(", ");
        for (String p : pairs) {
            System.out.println("<><>" + p);
            String[] kv = p.split("=");
            ret.put(kv[0], kv[1]);
        }
        NetworkActor networkActor = new NetworkActor(Integer.parseInt(ret.get("id")), ret.get("type"));
        return networkActor;
    }

}
