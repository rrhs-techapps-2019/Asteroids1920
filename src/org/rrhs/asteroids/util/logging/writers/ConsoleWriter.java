package org.rrhs.asteroids.util.logging.writers;

import org.rrhs.asteroids.util.logging.LogEntry;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class ConsoleWriter extends Writer
{
    private OutputStream out = new BufferedOutputStream(System.out);

    public ConsoleWriter()
    {
        super.hookShutdownErrorHandling(this::close);
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
