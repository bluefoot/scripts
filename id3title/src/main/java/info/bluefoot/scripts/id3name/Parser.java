package info.bluefoot.scripts.id3name;

import info.bluefoot.scripts.eventlistener.ConsoleEventListener;
import info.bluefoot.scripts.eventlistener.EventListener;
import info.bluefoot.scripts.threadpool.WorkerPool;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {

    public static final String DEFAULT_PATH_NAME = "/home/bluefoot/";
    public static final String[] DEFAULT_EXTENSIONS = { "mp3" };
    private static final int NUMBER_OF_THREADS = 50;
    protected static final Logger logger = LoggerFactory.getLogger(Parser.class);
    protected static final EventListener ev = new ConsoleEventListener(); 

    private File path;
    private Boolean includeSubDirs;
    private WorkerPool worker = new WorkerPool(Parser.NUMBER_OF_THREADS);

    public Parser() {
        this(Parser.DEFAULT_PATH_NAME);
    }
    
    public Parser(String pathName) {
        this.path = new File(pathName);
        this.includeSubDirs = true;
    }
    
    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public Boolean getIncludeSubDirs() {
        return includeSubDirs;
    }

    public void setIncludeSubDirs(Boolean includeSubDirs) {
        this.includeSubDirs = includeSubDirs;
    }

    public void parse() {
        Collection<File> files = FileUtils.listFiles(this.path, Parser.DEFAULT_EXTENSIONS, this.includeSubDirs);
        logger.info(String.format("Parsing %s files in %s%s", files.size(), this.path, this.includeSubDirs ? ", including sub directories" : ""));
        ev.setTotal(files.size());
        for (File file : files) {
            worker.add(new SaveTitleBasedOnFileNameJob(file, ev));
        }
        worker.execute();
    }
}
