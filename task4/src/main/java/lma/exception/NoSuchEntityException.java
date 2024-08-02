package lma.exception;

public class NoSuchEntityException extends IllegalArgumentException {
    public NoSuchEntityException() {
        super();
        setStackTrace(new StackTraceElement[0]);
    }

    public NoSuchEntityException(String s) {
        super(s);
        setStackTrace(new StackTraceElement[0]);
    }
}
