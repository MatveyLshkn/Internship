package lma.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConstants {

    public static final String REGISTER_JSON_BODY = """
                {
                    "username": "register",
                    "password": "register",
                    "email": "register@example.com",
                    "roles":[
                            {"role":"USER"}
                    ]
                }
            """;

    public static final String REGISTER_FOR_LOGIN_JSON_BODY = """
                {
                    "username": "login",
                    "password": "login",
                    "email": "login@example.com",
                    "roles":[
                            {"role":"USER"}
                    ]
                }
            """;

    public static final String REGISTER_FOR_REFRESH_TOKEN_JSON_BODY = """
                {
                    "username": "refresh",
                    "password": "refresh",
                    "email": "refresh@example.com",
                    "roles":[
                            {"role":"USER"}
                    ]
                }
            """;

    public static final String LOGIN_JSON_BODY = """
                {
                    "username": "login",
                    "password": "login"
                }
            """;

    public static final String REFRESH_JSON_BODY = """
                {
                  "refreshToken": "%s"
                }
            """;

    public static final String ACCESS_TOKEN_JSON_PATH_KEYWORD = "$.accessToken";

    public static final String REFRESH_TOKEN_JSON_PATH_KEYWORD = "$.refreshToken";

    public static final String REFRESH_TOKEN_JSON_NODE_KEYWORD = "refreshToken";
}
