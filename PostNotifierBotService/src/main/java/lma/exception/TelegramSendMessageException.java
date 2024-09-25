package lma.exception;

public class TelegramSendMessageException extends RuntimeException{

    public TelegramSendMessageException() {
    }

    public TelegramSendMessageException(String message) {
        super(message);
    }

    public TelegramSendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
