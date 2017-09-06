package info.bluefoot.scripts.eventlistener;

import java.util.Date;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class SwingEventListener implements EventListener {

    private JProgressBar progressBar;
    private JTextArea textArea;

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void progressMade() {
        int value = progressBar.getValue();
        progressBar.setVisible(true);
        progressBar.setIndeterminate(false);
        progressBar.setValue(++value);
    }

    public void setTotal(int total) {
        progressBar.setMaximum(total);
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void addStatus(String status) {
        textArea.setText(textArea.getText() + System.getProperty("line.separator") + status);
    }

    @Override
    public void addStatus(Date date, String status) {
        status = new Date() + status;
        textArea.setText(textArea.getText() + System.getProperty("line.separator") + status);
    }

}
