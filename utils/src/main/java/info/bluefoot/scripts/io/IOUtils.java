package info.bluefoot.scripts.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    public static List<String> readStream(InputStream stream) throws IOException {
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            try {
                reader.close();
            } catch (Exception e) {}
        }
        return lines;
    }
    
    public static String readStreamAsString(InputStream stream) throws IOException {
        StringBuilder lines = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.append(line + System.getProperty("line.separator"));
            }
        } finally {
            try {
                reader.close();
            } catch (Exception e) {}
        }
        return lines.toString();
    }
}
