package org.rrhs.asteroids.util.logging;

public enum LogLevel
{
    NONE(6),
    FATAL(5),
    ERROR(4),
    WARN(3),
    INFO(2),
    DEBUG(1),
    TRACE(0),
    ALL(-1);

    private final int ordinal;

    LogLevel(final int ordinal)
    {
        this.ordinal = ordinal;
    }

    boolean shouldLogAt(LogLevel o)
    {
        return (this.ordinal >= o.ordinal);
    }
}