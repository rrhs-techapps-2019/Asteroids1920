package org.rrhs.asteroids.util;

public class NetworkUtils
{
    /**
     * A set of valid messages that can be sent to the server.<br>
     */
    public enum Message
    {
        START_MOVE("move"),
        STOP_MOVE("stop move"),
        START_TURN_LEFT("turn left"),
        START_TURN_RIGHT("turn right"),
        STOP_TURN("stop turn"),
        UPDATE("update");

        private final String raw;

        Message(final String raw)
        {
            this.raw = raw;
        }

        public String getRaw()
        {
            return raw;
        }
    }
}
