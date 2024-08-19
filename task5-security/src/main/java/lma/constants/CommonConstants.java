package lma.constants;

import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class CommonConstants {

    public static final String BASE_AUTH_PATH = "/auth";

    public static final String REGISTER_ENDPOINT = BASE_AUTH_PATH + "/register";

    public static final String LOGIN_ENDPOINT = BASE_AUTH_PATH + "/login";

    public static final String REFRESH_ENDPOINT = BASE_AUTH_PATH + "/refresh";

    public static final String BASE_GREETING_ENDPOINT = "/greeting";

    public static final String ADMIN_GREETING_ENDPOINT = BASE_GREETING_ENDPOINT + "/admin";

    public static final String USER_GREETING_ENDPOINT = BASE_GREETING_ENDPOINT + "/user";

    public static final String SECRET = "c249d0b92ede74cf785d9bc00d39b9ebf393117c212706af54b5520ab8467a28";

    public static final String ROLES_CLAIM_NAME = "roles";

    public static final String IP_CLAIM_NAME = "ip";

    public static final String TOKEN_EXPIRED_MESSAGE = "Token expired";

    public static final String UNSUPPORTED_JWT_MESSAGE = "Unsupported JWT";

    public static final String MALFORMED_JWT_MESSAGE = "Malformed JWT";

    public static final String INVALID_SIGNATURE_MESSAGE = "Invalid signature";

    public static final String INVALID_TOKEN_MESSAGE = "Invalid token";

    public static final String ADMIN_ROLE_NAME = "ADMIN";

    public static final String USER_ROLE_NAME = "USER";

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    public static final String BEGINNING_AUTH_HEADER_NAME = "Bearer ";

    public static final Instant DEFAULT_ACCESS_TOKEN_TTL = Instant.ofEpochSecond(5 * 60 * 1000);

    public static final Instant DEFAULT_REFRESH_TOKEN_TTL = Instant.ofEpochSecond(24 * 60 * 60 * 1000);
}
