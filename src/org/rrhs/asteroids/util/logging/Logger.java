package org.rrhs.asteroids.util.logging;

import org.rrhs.asteroids.util.logging.writers.Writer;

import java.io.IOException;
import java.time.Instant;

/**
 * A basic static logger.
 */
public final class Logger
{
    // Non-instantiable
    private Logger()
    {
    }

    // --- LogLevel.FATAL ---

    /**
     * Log a message at {@link LogLevel#FATAL}.
     *
     * @param message Message to log
     */
    public static void fatal(final String message)
    {
        log(message, LogLevel.FATAL);
    }

    /**
     * Log a message at {@link LogLevel#FATAL}.
     *
     * @param o Object to log – should have a meaningful {@link #toString} method
     */
    public static void fatal(final Object o)
    {
        log(o.toString(), LogLevel.FATAL);
    }

    /**
     * Log a {@link Throwable} at {@link LogLevel#FATAL}.
     *
     * @param e Throwable to log
     */
    public static void fatal(final Throwable e)
    {
        log(e.getMessage(), LogLevel.FATAL);
    }

    // --- LogLevel.ERROR ---

    /**
     * Log a message at {@link LogLevel#ERROR}.
     *
     * @param message Message to log
     */
    public static void error(final String message)
    {
        log(message, LogLevel.ERROR);
    }

    /**
     * Log a message at {@link LogLevel#ERROR}.
     *
     * @param o Object to log – should have a meaningful {@link #toString} method
     */
    public static void error(final Object o)
    {
        log(o.toString(), LogLevel.ERROR);
    }

    /**
     * Log a {@link Throwable} at {@link LogLevel#ERROR}.
     *
     * @param e Throwable to log
     */
    public static void error(final Throwable e)
    {
        log(e.getMessage(), LogLevel.ERROR);
    }

    // --- LogLevel.WARN ---

    /**
     * Log a message at {@link LogLevel#WARN}.
     *
     * @param message Message to log
     */
    public static void warn(final String message)
    {
        log(message, LogLevel.WARN);
    }

    /**
     * Log a message at {@link LogLevel#WARN}.
     *
     * @param o Object to log – should have a meaningful {@link #toString} method
     */
    public static void warn(final Object o)
    {
        log(o.toString(), LogLevel.WARN);
    }

    /**
     * Log a {@link Throwable} at {@link LogLevel#WARN}.
     *
     * @param e Throwable to log
     */
    public static void warn(final Throwable e)
    {
        log(e.getMessage(), LogLevel.WARN);
    }

    // --- LogLevel.INFO ---

    /**
     * Log a message at {@link LogLevel#INFO}.
     *
     * @param message Message to log
     */
    public static void info(final String message)
    {
        log(message, LogLevel.INFO);
    }

    /**
     * Log a message at {@link LogLevel#INFO}.
     *
     * @param o Object to log – should have a meaningful {@link #toString} method
     */
    public static void info(final Object o)
    {
        log(o.toString(), LogLevel.INFO);
    }

    /**
     * Log a {@link Throwable} at {@link LogLevel#INFO}.
     *
     * @param e Throwable to log
     */
    public static void info(final Throwable e)
    {
        log(e.getMessage(), LogLevel.INFO);
    }

    // --- LogLevel.DEBUG ---

    /**
     * Log a message at {@link LogLevel#DEBUG}.
     *
     * @param message Message to log
     */
    public static void debug(final String message)
    {
        log(message, LogLevel.DEBUG);
    }

    /**
     * Log a message at {@link LogLevel#DEBUG}.
     *
     * @param o Object to log – should have a meaningful {@link #toString} method
     */
    public static void debug(final Object o)
    {
        log(o.toString(), LogLevel.DEBUG);
    }

    /**
     * Log a {@link Throwable} at {@link LogLevel#DEBUG}.
     *
     * @param e Throwable to log
     */
    public static void debug(final Throwable e)
    {
        log(e.getMessage(), LogLevel.DEBUG);
    }

    // --- LogLevel.TRACE ---

    /**
     * Log a message at {@link LogLevel#TRACE}.
     *
     * @param message Message to log
     */
    public static void trace(final String message)
    {
        log(message, LogLevel.TRACE);
    }

    /**
     * Log a message at {@link LogLevel#TRACE}.
     *
     * @param o Object to log – should have a meaningful {@link #toString} method
     */
    public static void trace(final Object o)
    {
        log(o.toString(), LogLevel.TRACE);
    }

    /**
     * Log a {@link Throwable} at {@link LogLevel#TRACE}.
     *
     * @param e Throwable to log
     */
    public static void trace(final Throwable e)
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
        // two log calls (one for level, one for this method), so we skip them entirely
        final StackTraceElement trace = Thread.currentThread().getStackTrace()[3];
        final String callingClass = trace.getClassName();
        final String callingMethod = trace.getMethodName();
        final int lineNumber = trace.getLineNumber();

        // Write entry to all registered writers
        final LogEntry entry = new LogEntry(message + '\n', level, timestamp, config, callingClass, callingMethod, lineNumber);
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
