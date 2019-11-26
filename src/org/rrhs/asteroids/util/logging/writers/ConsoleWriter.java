package org.rrhs.asteroids.util.logging.writers;

import org.rrhs.asteroids.util.logging.LogEntry;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * A {@link Writer} that outputs log entries to an output stream.
 */
public final class ConsoleWriter extends Writer
{
    private final ScheduledExecutorService flushExecutor = new ScheduledThreadPoolExecutor(1);
    private OutputStream out;

    /**
     * Create a new ConsoleWriter using the specified output stream.
     *
     * @param out      Output stream to use
     * @param buffered Whether or not the stream should be buffered internally
     */
    public ConsoleWriter(final OutputStream out, boolean buffered)
    {
        if (buffered) this.out = new BufferedOutputStream(out, DEFAULT_BUFFER_SIZE);
        else this.out = out;
        super.hookShutdownErrorHandling(this::close);
    }

    /**
     * Create a new ConsoleWriter using the specified output stream.<br>
     * Unbuffered by default.
     *
     * @param out Output stream to use
     */
    public ConsoleWriter(final OutputStream out)
    {
        this(out, false);
    }

    /**
     * Create a new ConsoleWriter using {@link System#out}.<br>
     *
     * @param buffered Whether or not the stream should be buffered internally
     */
    public ConsoleWriter(boolean buffered)
    {
        this(System.out, buffered);
    }

    /**
     * Create a new ConsoleWriter using {@link System#out}.<br>
     * Unbuffered by default.
     */
    public ConsoleWriter()
    {
        this(System.out, false);
    }

    @Override
    public void write(LogEntry entry) throws IOException
    {
        if (entry.shouldLog())
        {
            out.write(entry.toString().getBytes());
        }
    }

    @Override
    public void flush() throws IOException
    {
        out.flush();
    }

    @Override
    public void close() throws IOException
    {
        out.close();
    }
}
