package org.rrhs.asteroids.util.logging;

import org.rrhs.asteroids.util.logging.writers.Writer;

import java.io.IOException;
import java.time.Instant;

/**
 * A basic static logger.
 */
public final class Logger
{
    public static void fatal(final String message)
    {
        log(message, LogLevel.FATAL);
    }

    public static void fatal(final Exception e)
    {
        log(e.getMessage(), LogLevel.FATAL);
    }

    public static void error(final String message)
    {
        log(message, LogLevel.ERROR);
    }

    public static void error(final Exception e)
    {
        log(e.getMessage(), LogLevel.ERROR);
    }

    public static void warn(final String message)
    {
        log(message, LogLevel.WARN);
    }

    public static void warn(final Exception e)
    {
        log(e.getMessage(), LogLevel.WARN);
    }

    public static void info(final String message)
    {
        log(message, LogLevel.INFO);
    }

    public static void info(final Exception e)
    {
        log(e.getMessage(), LogLevel.INFO);
    }

    public static void debug(final String message)
    {
        log(message, LogLevel.DEBUG);
    }

    public static void debug(final Exception e)
    {
        log(e.getMessage(), LogLevel.DEBUG);
    }

    public static void trace(final String message)
    {
        log(message, LogLevel.TRACE);
    }

    public static void trace(final Exception e)
    {
        log(e.getMessage(), LogLevel.TRACE);
    }

    private static void log(final String message, final LogLevel level)
    {
        // Timestamp and config
        final Instant timestamp = Instant.now();
        final LoggerConfig config = LoggerConfigurator.getActive();

        // Trace caller
        // First 3 elements in stacktrace array are the getStackTrace() call and
        // two log calls, so we skip them entirely
        final StackTraceElement trace = Thread.currentThread().getStackTrace()[3];
        final String callingClass = trace.getClassName();
        final String callingMethod = trace.getMethodName();
        final int lineNumber = trace.getLineNumber();

        // Write entry
        final LogEntry entry = new LogEntry(message, level, timestamp, config, callingClass, callingMethod, lineNumber);
        for (final Writer writer : config.getWriters())
        {
            try
            {
                writer.write(entry);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
