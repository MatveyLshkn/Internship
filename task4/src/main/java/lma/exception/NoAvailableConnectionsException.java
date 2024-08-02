package lma.exception;

import java.util.NoSuchElementException;

public class NoAvailableConnectionsException extends NoSuchElementException {
    public NoAvailableConnectionsException() {
        super();
        setStackTrace(new StackTraceElement[0]);
    }

    public NoAvailableConnectionsException(String message) {
        super(message);
        setStackTrace(new StackTraceElement[0]);
    }
}
