package lma.constants;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.Instant;

@UtilityClass
public class CommonConstants {

    public static final String API_VERSION = "/v1";

    public static final String BASE_PATH = "/api" + API_VERSION;

    public static final String BASE_AUTH_PATH = BASE_PATH + "/auth";

    public static final String REGISTER_ENDPOINT = BASE_AUTH_PATH + "/register";

    public static final String LOGIN_ENDPOINT = BASE_AUTH_PATH + "/login";

    public static final String REFRESH_ENDPOINT = BASE_AUTH_PATH + "/refresh";

    public static final String BASE_GREETING_ENDPOINT = BASE_PATH + "/greeting";

    public static final String ADMIN_GREETING_ENDPOINT = BASE_GREETING_ENDPOINT + "/admin";

    public static final String USER_GREETING_ENDPOINT = BASE_GREETING_ENDPOINT + "/user";

    public static final String OPENAPI_PATH = "/v3/api-docs/**";

    public static final String SWAGGER_UI_PATH = "/swagger-ui/**";

    public static final String SECRET = "c249d0b92ede74cf785d9bc00d39b9ebf393117c212706af54b5520ab8467a28";

    public static final String ROLES_CLAIM_NAME = "roles";

    public static final String IP_CLAIM_NAME = "ip";

    public static final String ADMIN_ROLE_NAME = "ADMIN";

    public static final String USER_ROLE_NAME = "USER";

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    public static final String BEGINNING_AUTH_HEADER_NAME = "Bearer ";

    public static final Duration DEFAULT_ACCESS_TOKEN_TTL = Duration.ofMinutes(5);

    public static final Duration DEFAULT_REFRESH_TOKEN_TTL = Duration.ofDays(1);
}