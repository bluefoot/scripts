package info.bluefoot.scripts.eventlistener;

import java.util.Date;

public interface EventListener {

    String DATE_FORMAT="[dd/MM/yyyy HH:mm:ss] ";

    /**
     * Progress made.
     */
    public void progressMade();

    /**
     * Sets the total.
     *
     * @param total the new total
     */
    public void setTotal(int total);

    /**
     * Adds new status.
     *
     * @param status the status
     */
    public void addStatus(String status);

    /**
     * Adds new status and concats the date in the beginning.
     *
     * @param date the date
     * @param status the status
     */
    public void addStatus(Date date, String status);
}
