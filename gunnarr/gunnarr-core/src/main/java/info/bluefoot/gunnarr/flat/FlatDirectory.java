package info.bluefoot.gunnarr.flat;

import info.bluefoot.gunnarr.DirOperation;
import info.bluefoot.gunnarr.exception.FlattableException;
import info.bluefoot.gunnarr.exception.InvalidDirectoryException;
import info.bluefoot.gunnarr.filteroptions.FilterOptions;
import info.bluefoot.scripts.eventlistener.EventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Flats all files in a directory and it's subdirs
 */
public class FlatDirectory implements DirOperation {

    // ~ Instance variables =================================================

    private File directory;
    private FilterOptions options;
    private List<EventListener> eventListeners;

    // ~ Constructors =======================================================

    public FlatDirectory(String directory, FilterOptions opt) throws InvalidDirectoryException {
        setDirectory(new File(directory));
        setOptions(opt);
    }

    // ~ Getters/setters ====================================================

    public void setDirectory(File directory) throws InvalidDirectoryException {
        if (!directory.isDirectory()) {
            throw new InvalidDirectoryException("This is not a directory");
        }
        this.directory = directory;
    }

    public File getDirectory() {
        return directory;
    }

    public void setOptions(FilterOptions opt) {
        this.options = opt;
    }

    public void addEventListener(EventListener e) {
        if(this.eventListeners==null) {
            this.eventListeners = new ArrayList<EventListener>();
        }
        this.eventListeners.add(e);
    }

    // ~ Action methods ====================================================

    /**
     * Recursevely walk into folders and move the files to the parent directory.
     *
     * @throws FlattableException in case of anything goes wrong
     * @throws OptionsException if the option file was not setted
     */
    public void start() throws FlattableException {
        new FlatWalker().flat(this.directory, this.options, this.eventListeners);
    }
}
