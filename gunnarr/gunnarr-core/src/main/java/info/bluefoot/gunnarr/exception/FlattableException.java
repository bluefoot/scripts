package info.bluefoot.gunnarr.exception;

public class FlattableException extends RuntimeException {

    public FlattableException(Throwable cause) {
        super(cause);
    }

    public FlattableException(String msg) {
        super(msg);
    }

    public FlattableException(String msg, Throwable cause) {
        super(msg, cause);
    }

    private static final long serialVersionUID = 1L;

}
