package info.bluefoot.scripts.eventlistener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class ConsoleEventListener implements EventListener {

    private int atual;
    
    public void progressMade() {
        System.out.print("-");
        this.atual++;
    }

    public void setTotal(int total) {
        this.atual = 0;
        String bars = StringUtils.repeat("=", total);
        System.out.println(bars);
        String blanks = StringUtils.repeat(" ", this.atual);
        System.out.print(blanks);
    }

    public void addStatus(String status) {
        addStatus(new Date(), status);
    }
    
    public void addStatus(Date date, String status) {
        SimpleDateFormat sf = new SimpleDateFormat(EventListener.DATE_FORMAT);
        String str = sf.format(new Date()) + status;
        if(!StringUtils.endsWith(str, System.getProperty("line.separator"))) {
            str+= System.getProperty("line.separator");
        }
        System.out.println(str);
    }

}
