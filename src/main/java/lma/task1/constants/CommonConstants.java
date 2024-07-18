package lma.task1.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {

    public static final String JSON_DATA_MEMBER_NAME = "data";

    public static final String JSON_SETTINGS_MEMBER_NAME = "settings";

    public static final String JSON_FILE_NOT_FOUND_MESSAGE = "Json file not found: %s";

    public static final String USER_NOT_FOUND_MESSAGE = "User with id %d not found";

    public static final String WRONG_ENUM_NAME_MESSAGE = "Wrong enum name: %s";

    public static final String MISSING_SORT_SETTING_MESSAGE = "Missing sortBy setting";

    public static final String MISSING_USD_COST_SETTING_MESSAGE = "Missing startCostUSD setting";

    public static final String MISSING_EUR_COST_SETTING_MESSAGE = "Missing startCostEUR setting";

    public static final String MISSING_SETTINGS_FILE_MESSAGE = "Missing settings file";

    public static final String EMPTY_JSON = "{}";

    public static final String JSON_DATA_TEMPLATE = "{\n \"data\": %s\n}";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final Integer ERROR_STATUS_CODE = 1;

    public static final Double NO_MULTIPLIER_VALUE = 1.0;
}
