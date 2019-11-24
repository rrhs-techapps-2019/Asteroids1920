package org.rrhs.asteroids.util.logging.writers;

import org.rrhs.asteroids.util.logging.LogEntry;
import org.rrhs.asteroids.util.logging.Logger;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * A Writer that outputs log entries to a file.
 */
public final class FileWriter extends Writer
{
    private OutputStream out;

    public FileWriter(String fileName)
    {
        try
        {
            final OutputStream unbuffered = Files.newOutputStream(Paths.get(fileName),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            this.out = new BufferedOutputStream(unbuffered, DEFAULT_BUFFER_SIZE);
        }
        catch (InvalidPathException e)
        {
            Logger.error(fileName + " is not a valid file path");
        }
        catch (IOException e)
        {
            Logger.error("I/O error while opening output stream for " + fileName);
        }

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
