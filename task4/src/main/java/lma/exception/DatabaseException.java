package lma.exception;

import java.sql.SQLException;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String reason) {
        super(reason);
        setStackTrace(new StackTraceElement[0]);
    }

    public DatabaseException() {
        super();
        setStackTrace(new StackTraceElement[0]);
    }
}
