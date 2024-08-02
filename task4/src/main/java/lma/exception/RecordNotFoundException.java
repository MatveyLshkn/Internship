package lma.exception;

public class RecordNotFoundException extends IllegalArgumentException{
    public RecordNotFoundException() {
        super();
        setStackTrace(new StackTraceElement[0]);
    }

    public RecordNotFoundException(String s) {
        super(s);
    }
}
