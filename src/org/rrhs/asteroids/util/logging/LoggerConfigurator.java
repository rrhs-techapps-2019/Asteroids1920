package org.rrhs.asteroids.util.logging;

import org.rrhs.asteroids.util.logging.writers.ConsoleWriter;
import org.rrhs.asteroids.util.logging.writers.Writer;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public final class LoggerConfigurator
{
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yy HH:mm:ss")
            .withLocale(Locale.US)
            .withZone(ZoneId.systemDefault());
    private static final AtomicReference<LoggerConfig> CURRENT = new AtomicReference<>();

    static
    {
        // Default configuration
        LoggerConfigurator.empty()
                .addWriter(new ConsoleWriter())
                .activate();
    }

    List<Writer> writers = new ArrayList<>();
    DateTimeFormatter formatter;
    LogLevel level;

    private LoggerConfigurator(DateTimeFormatter formatter, LogLevel level)
    {
        this.formatter = formatter;
        this.level = level;
    }

    /**
     * Create a new LoggerConfigurator with the default configuration.<br>
     * (i.e. ISO timestamp format, LogLevel.INFO)
     */
    public static LoggerConfigurator empty()
    {
        return new LoggerConfigurator(DEFAULT_FORMATTER, LogLevel.INFO);
    }

    /**
     * Get the currently active LoggerConfig.
     */
    static LoggerConfig getActive()
    {
        return CURRENT.get();
    }

    /**
     * Set the log level.
     *
     * @return this LoggerConfigurator
     */
    public LoggerConfigurator level(LogLevel level)
    {
        this.level = level;
        return this;
    }

    /**
     * Set the timestamp format pattern.
     *
     * @param pattern Format pattern
     * @return this LoggerConfigurator
     * @see DateTimeFormatter#ofPattern
     */
    public LoggerConfigurator format(String pattern)
    {
        this.formatter = DateTimeFormatter.ofPattern(pattern)
                .withLocale(Locale.US)
                .withZone(ZoneId.systemDefault());
        return this;
    }

    /**
     * Add a {@linkplain Writer}.
     *
     * @param writer Writer to add
     * @return this LoggerConfigurator
     */
    public LoggerConfigurator addWriter(Writer writer)
    {
        writers.add(writer);
        return this;
    }

    /**
     * Activate the configuration defined by this LoggerConfigurator.
     */
    public void activate()
    {
        CURRENT.set(new LoggerConfig(this));
    }
}
