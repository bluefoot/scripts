package info.bluefoot.scripts.id3name;

import info.bluefoot.scripts.eventlistener.EventListener;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A job that receives a mp3 file and changes the id3 title attribute to it's 
 * name (without extension). 
 * @author gewton
 *
 */
public class SaveTitleBasedOnFileNameJob implements Runnable {
    protected static final Logger logger = LoggerFactory.getLogger(SaveTitleBasedOnFileNameJob.class);
    private File file;
    private EventListener ev;
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    public SaveTitleBasedOnFileNameJob(File file, EventListener ev) {
        this.file = file;
        this.ev = ev;
    }

    @Override
    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug("Parsing " + file.getAbsolutePath());
        }
        
        if (file.isDirectory()) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Cannot parse %s, it's a directory", file.getAbsolutePath()));
            }
            throw new ParsingException("This is a directory: " + file.getAbsolutePath());
        }
        try {
            AudioFile f = AudioFileIO.read(file);
            Tag tag = f.getTag();
            tag.setField(FieldKey.TITLE, FilenameUtils.getBaseName(file.getName()));
            f.commit();
            if (logger.isDebugEnabled()) {
                logger.debug(file.getAbsolutePath() + " Concluded");
            }
            ev.progressMade();
        } catch (CannotReadException e) {
            throw new ParsingException("Error on reading file", e);
        } catch (IOException e) {
            throw new ParsingException("Error on reading file", e);
        } catch (TagException e) {
            throw new ParsingException("Error on reading tag", e);
        } catch (ReadOnlyFileException e) {
            throw new ParsingException("File is ready only", e);
        } catch (InvalidAudioFrameException e) {
            throw new ParsingException("Error on reading file", e);
        } catch (CannotWriteException e) {
            throw new ParsingException("Error on writing file", e);
        }

    }

}
