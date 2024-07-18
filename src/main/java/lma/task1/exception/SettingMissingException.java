package lma.task1.exception;

import java.io.IOException;
import java.io.Serial;

public class SettingMissingException extends IOException {

    public SettingMissingException() {
    }

    public SettingMissingException(String checkMessage) {
        super(checkMessage);
    }
}
