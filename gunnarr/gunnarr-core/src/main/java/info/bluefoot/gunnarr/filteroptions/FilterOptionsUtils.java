package info.bluefoot.gunnarr.filteroptions;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FilterOptionsUtils {

    public static Collection<File> getFilesBasedOnFilterOptions(File baseDir, FilterOptions options) {
        WildcardFileFilter fileFilter = new WildcardFileFilter(options.getWildCard());
        IOFileFilter dirFilter = null;
        return FileUtils.listFiles(baseDir, fileFilter, dirFilter);
    }
}
