package org.rrhs.asteroids.util.logging.writers;

import org.rrhs.asteroids.util.logging.LogEntry;

import java.io.IOException;

public abstract class Writer
{
    static final int DEFAULT_BUFFER_SIZE = 4096;

    /**
     * Output a {@link LogEntry} with this Writer.
     *
     * @throws IOException if an internal I/O exception occurs
     */
    public abstract void write(LogEntry entry) throws IOException;

    /**
     * Flush the underlying I/O stream used by this Writer.
     *
     * @throws IOException if an internal I/O exception occurs
     */
    public abstract void flush() throws IOException;

    /**
     * Close all I/O resources associated with this Writer.
     *
     * @throws IOException if an internal I/O exception occurs
     */
    public abstract void close() throws IOException;

    /**
     * Add a JVM shutdown hook with exception handling.<br>
     * Useful for making sure Writer streams get flushed when the JVM exits.
     * <p>
     * The implications in calling this method are entirely the same as those
     * for {@link Runtime#addShutdownHook}.
     *
     * @param hook Hook to register
     * @see Runtime#addShutdownHook
     */
    void hookShutdownErrorHandling(ErrorProneRunnable hook)
    {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try
            {
                hook.run();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }));
    }

    /**
     * Identical to {@link Runnable}, but capable of throwing an {@link Exception}
     * when {@link #run} is called.
     */
    @FunctionalInterface
    interface ErrorProneRunnable
    {
        void run() throws Exception;
    }
}