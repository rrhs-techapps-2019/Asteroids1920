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
    public Map<String, String> parseMessage(String message) {
        HashMap<String, String> ret = new HashMap<>();
        message = message.substring(1, message.length() - 1);
        String[] pairs = message.split(", ");
        for (String p : pairs) {
            String[] kv = p.split("=");
            ret.put(kv[0], kv[1]);
        }
        return ret;
    }

}
