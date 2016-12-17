package net.thoughtmachine.exception;

/**
 * Created by Łukasz Kwasek on 17/12/2016.
 */
public class InvalidActionException extends Exception {

    public InvalidActionException() {
    }

    public InvalidActionException(String message) {
        super(message);
    }

    public InvalidActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidActionException(Throwable cause) {
        super(cause);
    }

    public InvalidActionException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
