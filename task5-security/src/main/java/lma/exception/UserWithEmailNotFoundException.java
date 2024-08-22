package lma.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class UserWithEmailNotFoundException extends OAuth2AuthenticationException {

    public UserWithEmailNotFoundException(String message) {
        super(message);
    }
}
