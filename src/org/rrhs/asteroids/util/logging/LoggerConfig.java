package org.rrhs.asteroids.util.logging;

import org.rrhs.asteroids.util.logging.writers.Writer;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

final class LoggerConfig
{
    private final List<Writer> writers;
    private final DateTimeFormatter formatter;
    private final LogLevel level;

    LoggerConfig(LoggerConfigurator configurator)
    {
        this.writers = Collections.unmodifiableList(configurator.writers);
        this.formatter = configurator.formatter;
        this.level = configurator.level;
    }

    List<Writer> getWriters()
    {
        return writers;
    }

    LogLevel getLevel()
    {
        return level;
    }

    String format(final Instant timestamp)
    {
        return formatter.format(timestamp);
    }
}
