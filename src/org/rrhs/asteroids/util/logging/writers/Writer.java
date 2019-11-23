package org.rrhs.asteroids.util.logging.writers;

import org.rrhs.asteroids.util.logging.LogEntry;

import java.io.IOException;

public abstract class Writer
{
    public abstract void write(LogEntry entry) throws IOException;

    public abstract void flush() throws IOException;

    public abstract void close() throws IOException;

    protected void hookShutdownErrorHandling(ErrorProneRunnable hook)
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

    @FunctionalInterface
    protected interface ErrorProneRunnable
    {
        void run() throws Exception;
    }
}