package info.bluefoot.gunnarr.exception;

public class RandomableException extends FlattableException {

    public RandomableException(String msg) {
        super(msg);
    }

    public RandomableException(Throwable cause) {
        super(cause);
    }

    public RandomableException(String msg, Throwable cause) {
        super(msg, cause);
    }

    private static final long serialVersionUID = 1L;

}
