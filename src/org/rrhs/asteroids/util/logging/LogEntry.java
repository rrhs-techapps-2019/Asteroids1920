package org.rrhs.asteroids.util.logging;

import java.time.Instant;

public final class LogEntry
{
    private final String message;
    private final LogLevel level;
    private final Instant timestamp;
    private final LoggerConfig config;
    private final String callingClass;
    private final String callingMethod;
    private final int lineNumber;

    LogEntry(String message, LogLevel level, Instant timestamp, LoggerConfig config, String callingClass, String callingMethod, int lineNumber)
    {
        this.message = message;
        this.level = level;
        this.timestamp = timestamp;
        this.config = config;
        this.callingClass = callingClass;
        this.callingMethod = callingMethod;
        this.lineNumber = lineNumber;
    }

    // jesus, this is unreadable
    // conforms to this format:
    // (0000-00-00T00:00:00)[CallingClass#callingMethod:line] LEVEL: message
    @Override
    public String toString()
    {
        return "(" + config.format(timestamp) + ")" +
                "[" + callingClass + "#" + callingMethod + ":" + lineNumber + "] " +
                level.toString() + ": " +
                message;
    }

    public boolean shouldLog()
    {
        return level.shouldLogAt(config.getLevel());
    }
}