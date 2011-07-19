package info.bluefoot.gunnarr.flat;

import info.bluefoot.gunnarr.exception.FlattableException;
import info.bluefoot.gunnarr.filteroptions.FilterOptions;
import info.bluefoot.gunnarr.filteroptions.FilterOptionsUtils;
import info.bluefoot.scripts.eventlistener.EventListener;
import info.bluefoot.scripts.eventlistener.EventListenerUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * A {@link DirectoryWalker} that flats all files in a directory and it's subdirs
 */
public class FlatWalker extends DirectoryWalker<File> {

    // ~ Instance variables =================================================

    private File startDirectory;
    private FilterOptions options;
    private List<EventListener> eventListeners;

    // ~ Constructors =======================================================

    public FlatWalker() {
        super();
    }

    public List<File> flat(File startDirectory, FilterOptions options,
            List<EventListener> eventListeners) throws FlattableException {
        this.startDirectory = startDirectory;
        this.options = options;
        this.eventListeners = eventListeners;
        List<File> results = new ArrayList<File>();
        try {
            walk(startDirectory, results);
        } catch (IOException e) {
            throw new FlattableException("Error flatting files", e);
        }
        return results;
    }

    protected boolean handleDirectory(File directory, int depth,
            Collection<File> results) throws IOException {
        //if depth is != 1, means that this file already has the same name
        //of it's ancestor, and wasn't copied.
/*        if (depth != 0) {
            return true;
        }*/

        if(!directory.getAbsoluteFile().equals(this.startDirectory)) {

            Collection<File> files = FilterOptionsUtils.getFilesBasedOnFilterOptions(directory, this.options);
            EventListenerUtils.setTotalEventListener(this.eventListeners, files.size());

            if (files.size() > 0) {
                String status = String.format("Movind %s files from %s to %s",
                        files.size(), directory, this.startDirectory);
                EventListenerUtils.addStatusEventListener(this.eventListeners, status);
            }
            for (File file : files) {
                try {
                    String status = "Moving " + file;
                    EventListenerUtils.addStatusEventListener(this.eventListeners, status);
                    FileUtils.moveFileToDirectory(file, this.startDirectory, false);
                    EventListenerUtils.progressMadeEventListener(this.eventListeners);
                } catch (FileExistsException e) {
                    String status = String.format("File already exists: %s",
                            FilenameUtils.concat(this.startDirectory.toString(),
                                    file.toString()));
                    EventListenerUtils.addStatusEventListener(this.eventListeners, status);
                }
            }
        }
        return true;
    }

    protected void handleDirectoryEnd(File directory, int depth,
            Collection<File> results) throws IOException {
        if (depth != 0) {
            String status = "DELETING directory " + directory;
            EventListenerUtils.addStatusEventListener(this.eventListeners, status);
            FileUtils.deleteDirectory(directory);
        }
    }
}