package lma.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionConstants {
    public static final String USER_ALREADY_EXISTS_EXCEPTION_MESSAGE = "User: %s already exists!";

    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User: %s not found!";

    public static final String WRONG_PASSWORD_EXCEPTION_MESSAGE = "Wrong password!";

    public static final String INVALID_TOKEN_EXCEPTION_MESSAGE = "Invalid token!";
}
