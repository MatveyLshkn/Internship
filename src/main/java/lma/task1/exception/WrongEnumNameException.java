package lma.task1.exception;

public class WrongEnumNameException extends IllegalArgumentException {
    public WrongEnumNameException() {
        super();
    }

    public WrongEnumNameException(String s) {
        super(s);
    }
}
