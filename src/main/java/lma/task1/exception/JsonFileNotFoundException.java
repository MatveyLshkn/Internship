package lma.task1.exception;

import java.io.FileNotFoundException;

public class JsonFileNotFoundException extends FileNotFoundException {
    public JsonFileNotFoundException() {
        super();
    }

    public JsonFileNotFoundException(String s) {
        super(s);
    }
}
