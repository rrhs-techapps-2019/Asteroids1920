package org.rrhs.asteroids.network.actions;
import org.rrhs.asteroids.network.Server;

import java.util.Map;

public interface NetworkAction {
    public void act(Server server, int clientID, Map<String, String> parsedMessage);
}
