package net.thoughtmachine.exception;

/**
 * Created by Łukasz Kwasek on 18/12/2016.
 */
public class ParseException extends RuntimeException {

    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    protected ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ParseException(String message, Object... args) {
        super(String.format(message, args));
    }
}
