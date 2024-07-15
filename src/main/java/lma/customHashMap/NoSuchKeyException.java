package lma.customHashMap;

import java.io.PrintStream;
import java.io.PrintWriter;

public class NoSuchKeyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Override
    public void printStackTrace(PrintStream s) {
        StackTraceElement[] stackTraceElements = this.getStackTrace();
        s.println(stackTraceElements[0].toString());
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        StackTraceElement[] stackTraceElements = this.getStackTrace();
        s.println(stackTraceElements[0].toString());
    }
}
