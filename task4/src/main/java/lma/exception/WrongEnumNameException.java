package lma.exception;

public class WrongEnumNameException extends IllegalArgumentException {
    public WrongEnumNameException() {
        super();
        setStackTrace(new StackTraceElement[0]);
    }

    public WrongEnumNameException(String s) {
        super(s);
        setStackTrace(new StackTraceElement[0]);
    }
}
