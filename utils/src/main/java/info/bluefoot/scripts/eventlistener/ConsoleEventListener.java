package info.bluefoot.scripts.eventlistener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleEventListener implements EventListener {

    private int current;
    private int total;

    public void progressMade() {
        this.current++;
        System.out.println(current*100/total + "%");
    }

    public void setTotal(int total) {
        this.current = 0;
        this.total = total;
    }

    public void addStatus(String status) {
        addStatus(new Date(), status);
    }

    public void addStatus(Date date, String status) {
        SimpleDateFormat sf = new SimpleDateFormat(EventListener.DATE_FORMAT);
        String str = sf.format(new Date()) + status;
        System.out.println(str);
    }

}
