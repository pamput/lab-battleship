package net.thoughtmachine.exception;

/**
 * Created by Łukasz Kwasek on 18/12/2016.
 */
public class ExpectedTestException extends RuntimeException {
    public ExpectedTestException() {
    }

    public ExpectedTestException(String message) {
        super(message);
    }

    public ExpectedTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpectedTestException(Throwable cause) {
        super(cause);
    }

    public ExpectedTestException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
