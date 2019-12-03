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
 * A {@link Writer} that outputs log entries to a file.
 */
public final class FileWriter extends Writer
{
    private OutputStream out;

    /**
     * Create a new FileWriter that outputs to a new file with the specified filename.
     *
     * @param fileName Name of the file to output to
     * @param buffered Whether or not the stream should be buffered internally
     */
    public FileWriter(String fileName, boolean buffered)
    {
        try
        {
            final OutputStream unbuffered = Files.newOutputStream(Paths.get(fileName),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            if (buffered) this.out = new BufferedOutputStream(unbuffered, DEFAULT_BUFFER_SIZE);
            else this.out = unbuffered;
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

    /**
     * Create a new FileWriter that outputs to a new file with the specified filename.<br>
     * Unbuffered by default.
     *
     * @param fileName Name of the file to output to
     */
    public FileWriter(String fileName)
    {
        this(fileName, false);
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
