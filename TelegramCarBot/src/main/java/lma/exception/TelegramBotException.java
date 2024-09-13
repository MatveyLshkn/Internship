package lma.exception;

public class TelegramBotException extends RuntimeException {

    public TelegramBotException() {
    }

    public TelegramBotException(String message) {
        super(message);
    }

    public TelegramBotException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramBotException(Throwable cause) {
        super(cause);
    }
}
