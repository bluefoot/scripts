package info.bluefoot.scripts.id3name;

public class ParsingException extends RuntimeException {

    public ParsingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ParsingException(String msg) {
        super(msg);
    }

}
