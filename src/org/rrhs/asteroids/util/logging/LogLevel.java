package org.rrhs.asteroids.util.logging;

enum LogLevel
{
    FATAL(5),
    ERROR(4),
    WARN(3),
    INFO(2),
    DEBUG(1),
    TRACE(0),
    ALL(-1);

    private final int level;

    LogLevel(final int level)
    {
        this.level = level;
    }

    boolean shouldLogAt(LogLevel o)
    {
        return (this.level > o.level);
    }
}