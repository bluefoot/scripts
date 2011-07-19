package info.bluefoot.scripts.eventlistener;

import java.util.List;

public class EventListenerUtils {
    public static void setTotalEventListener(List<EventListener> eventListeners, int total) {
        for (EventListener eventListener : eventListeners) {
            eventListener.setTotal(total);
        }
    }

    public static void progressMadeEventListener(List<EventListener> eventListeners) {
        for (EventListener eventListener : eventListeners) {
            eventListener.progressMade();
        }
    }

    public static void addStatusEventListener(List<EventListener> eventListeners, String status) {
        for (EventListener eventListener : eventListeners) {
            eventListener.addStatus(status);
        }
    }

}
