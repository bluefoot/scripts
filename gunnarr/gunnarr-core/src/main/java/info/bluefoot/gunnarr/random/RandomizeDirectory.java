package info.bluefoot.gunnarr.random;

import info.bluefoot.gunnarr.DirOperation;
import info.bluefoot.gunnarr.exception.InvalidDirectoryException;
import info.bluefoot.gunnarr.exception.RandomableException;
import info.bluefoot.gunnarr.filteroptions.FilterOptions;
import info.bluefoot.gunnarr.filteroptions.FilterOptionsUtils;
import info.bluefoot.scripts.eventlistener.EventListener;
import info.bluefoot.scripts.eventlistener.EventListenerUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;

/**
 * Operations relative to randomize a directory
 */
public class RandomizeDirectory implements DirOperation {

    // ~ Instance variables =================================================

    private static final int BASE_SIZE = 15;
    private File directory;
    private FilterOptions options;
    private List<EventListener> eventListeners;

    // ~ Constructors =======================================================

    public RandomizeDirectory(String directory, FilterOptions opt) throws InvalidDirectoryException {
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
     * Randomize all file names in all subdirectories of a directory.
     * Preserves the extension.
     *
     * @throws RandomableException in case of trouble
     * @throws OptionsException if the options was not setted
     */
    public void start() throws RandomableException {
        Collection<File> files = FilterOptionsUtils.getFilesBasedOnFilterOptions(this.directory, this.options);
        EventListenerUtils.setTotalEventListener(this.eventListeners, files.size());
        for (File file : files) {
            int sizeNewName = RandomizeDirectory.BASE_SIZE
                    + Integer.parseInt(RandomStringUtils.randomNumeric(1));
            String newName = RandomStringUtils.randomAlphanumeric(sizeNewName);
            renameFile(file, newName);
            EventListenerUtils.progressMadeEventListener(this.eventListeners);
        }
    }

    private boolean renameFile(File original, String newName) {
        String extension = FilenameUtils.getExtension(original.getAbsolutePath());
        newName = FilenameUtils.concat(original.getParent(), newName + "." + extension);
        String status = String.format("Renaming %s\t to %s", original, newName);
        EventListenerUtils.addStatusEventListener(this.eventListeners, status);
        return original.renameTo(new File(newName));
    }

}
