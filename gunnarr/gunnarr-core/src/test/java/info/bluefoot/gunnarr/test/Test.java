package info.bluefoot.gunnarr.test;

import info.bluefoot.gunnarr.DirOperation;
import info.bluefoot.gunnarr.filteroptions.FilterOptions;
import info.bluefoot.gunnarr.flat.FlatDirectory;
import info.bluefoot.gunnarr.random.RandomizeDirectory;
import info.bluefoot.scripts.eventlistener.ConsoleEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);
    private static String DIR = "~/asdf";

    public static void main(String[] args) {

        logger.debug("flatting...");

        ConsoleEventListener consoleEventListener = new ConsoleEventListener();

        DirOperation f = new FlatDirectory(DIR, new FilterOptions("*"));
        f.addEventListener(consoleEventListener);
        f.start();

        logger.debug("randomizing...");

        RandomizeDirectory r = new RandomizeDirectory(DIR, new FilterOptions("*"));
        r.addEventListener(consoleEventListener);
        r.start();
    }
}
