package info.bluefoot.gunnarr;

import info.bluefoot.gunnarr.exception.FlattableException;
import info.bluefoot.gunnarr.exception.InvalidDirectoryException;
import info.bluefoot.gunnarr.filteroptions.FilterOptions;
import info.bluefoot.scripts.eventlistener.EventListener;

import java.io.File;

/**
 * The interface {@link DirOperation} represents an operation that is done in a
 * directory, i.e. randomize a directory or flat a directory or clean, etc
 */
public interface DirOperation {

    /**
     * Sets the directory.
     *
     * @param directory the new directory
     * @throws InvalidDirectoryException the invalid directory exception
     */
    public void setDirectory(File directory) throws InvalidDirectoryException;

    /**
     * Gets the directory.
     *
     * @return the directory
     */
    public File getDirectory();

    /**
     * Sets the options.
     *
     * @param opt the new options
     */
    public void setOptions(FilterOptions opt);

    /**
     * Sets the event listener.
     *
     * @param e the new event listener
     */
    public void addEventListener(EventListener e);

    /**
     * Starts the operation
     */
    public void start() throws FlattableException;

}
