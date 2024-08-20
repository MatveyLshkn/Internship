package lma.exceptionHandler;

import lma.exception.InvalidTokenException;
import lma.exception.UserAlreadyExistsException;
import lma.exception.UserNotFoundException;
import lma.exception.WrongPasswordException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidAlgorithmParameterException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({UserAlreadyExistsException.class, WrongPasswordException.class, InvalidTokenException.class})
    protected ResponseEntity<String> handleException(RuntimeException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler({UserNotFoundException.class})
    protected ResponseEntity<String> handleNotFoundException(RuntimeException exception) {
        String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
