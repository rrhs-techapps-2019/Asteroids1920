package org.rrhs.asteroids.util.logging.writers;

import org.rrhs.asteroids.util.logging.LogEntry;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * A Writer that outputs log entries to an output stream.<br>
 * The stream is always buffered internally.
 */
public final class ConsoleWriter extends Writer
{
    private final ScheduledExecutorService flushExecutor = new ScheduledThreadPoolExecutor(1);
    private OutputStream out;

    /**
     * Create a new ConsoleWriter using the specified output stream.
     *
     * @implNote The stream will be buffered internally. Don't buffer it yourself.
     */
    public ConsoleWriter(final OutputStream out)
    {
        this.out = new BufferedOutputStream(out, DEFAULT_BUFFER_SIZE);
        super.hookShutdownErrorHandling(this::close);
    }

    /**
     * Create a new ConsoleWriter using {@link System#out}.
     */
    public ConsoleWriter()
    {
        this(System.out);
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
