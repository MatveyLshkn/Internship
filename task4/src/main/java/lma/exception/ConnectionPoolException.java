package lma.exception;

public class ConnectionPoolException extends RuntimeException {
    public ConnectionPoolException() {
        super();
        setStackTrace(new StackTraceElement[0]);
    }

    public ConnectionPoolException(String message) {
        super(message);
        setStackTrace(new StackTraceElement[0]);
    }
}
