package info.bluefoot.scripts.growl;

import info.bluefoot.scripts.applescript.AppleScriptHelper;

import java.io.IOException;

/**
 * Usage: <tt>Growl.getGrowl("your app name")</tt> <em>(will be used as a title too)</em>
 * @author bluefoot
 *
 */
public class Growl {
    
    private static Growl instance;
    public static Growl getGrowl(String appName) {
        if(instance==null) {
            instance=new Growl();
        }
        instance.applicationName=appName;
        return instance;
    }
    
    private String applicationName;
    
    private Growl() {
    }

    public void growl(final String msg, boolean sticky, String identifier) {
        final String stickyText=sticky?"yes":"no";
        final String identifierText=identifier.isEmpty()?"":"identifier \"" + identifier + "\"";
        new Thread(new Runnable() {
            public void run() {
                try {
                    String file = AppleScriptHelper.readResourceFile("/growl.applescript");
                    AppleScriptHelper.runAppleScript(String.format(file, 
                            msg, 
                            applicationName, 
                            applicationName, 
                            applicationName,
                            applicationName, identifierText, applicationName, applicationName, stickyText));
                } catch (IOException e) {
                    new Exception("Can't send growl notification", e).printStackTrace();
                    return; 
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
        }).start();
    }

    public void growl(String msg, boolean sticky) {
        growl(msg, sticky, "");
    }
    
    public void infoMessage(String msg, String identifier) {
        growl(msg, false, identifier);
    }
    
    public void infoMessage(String msg) {
        growl(msg, false, "");
    }
}
