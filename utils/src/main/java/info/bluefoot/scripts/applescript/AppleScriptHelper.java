package info.bluefoot.scripts.applescript;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppleScriptHelper {
    private static Logger logger = LoggerFactory.getLogger(AppleScriptHelper.class);
    /**
     * Opens a apple script resource file within the classpath and executes it
     * @param resourceName name of the resource
     */
    public static void runScriptResourceFile(String resourceName) {
        try {
            String script = readResourceFile(resourceName);
            runAppleScript(script);
        } catch (IOException e) {
            throw new RuntimeException("Can't read resource file", e);
        }
    }

    /**
     * Receives an apple script as input and executes it by writing to temp dir and running <tt>osascript</tt> command
     */
    public static void runAppleScript(String script) throws RuntimeException {
        
        String tempFileName = RandomStringUtils.randomAlphanumeric(14) + ".scpt";
        File tempFileScript = null;
        try {
            tempFileScript = new File(System.getProperty("java.io.tmpdir"), tempFileName);
            FileUtils.writeStringToFile(tempFileScript, script);
        } catch (IOException e) {
            throw new RuntimeException("Cannot write apple script to temp dir", e);
        } finally {
            if(tempFileScript != null) {
                tempFileScript.deleteOnExit();
            }
        }
        if(logger.isDebugEnabled()) logger.debug(String.format("Running script %s\nLocated at %s", script, tempFileScript.getAbsolutePath()));
        try {
            Runtime.getRuntime().exec("osascript " + tempFileScript.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Cannot execute temp apple script " + e);
        }
    }
    
    /**
     * Returns the content of a resource file
     */
    public static String readResourceFile(String resourceName) throws IOException {
        InputStream in = null;
        BufferedReader reader = null;
        StringBuilder sb;
        try {
            in = AppleScriptHelper.class.getResourceAsStream(resourceName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            sb = new StringBuilder();
            while(line != null) {
                sb.append(line + "\n");
                line = reader.readLine();
            }
        }finally {
            if(reader!=null) reader.close();
            if(in!=null) in.close();
        }
        return sb.toString();
    }
}
