package org.rrhs.asteroids.network;

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
        if (parser.length == 2)
            return new Packet(parser[0], parser[1]);
        else if (parser.length == 3)
            return new Packet(parser[0], parser[1], parser[2]);
        return null;
    }

}
