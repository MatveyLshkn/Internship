package lma.task1.constants;

import lma.task1.util.JsonUtil;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.util.regex.Pattern;

@UtilityClass
public class PathConstants {

    public static final String BASE_DATA_DIRECTORY_NAME = "data" + File.separator;

    public static final String SETTINGS_FILENAME = BASE_DATA_DIRECTORY_NAME +"settings.json";

    public static final String GENERAL_DATA_FILENAME = BASE_DATA_DIRECTORY_NAME + "db.json";

    public static final String BASE_FILE_PREFIX = "db_";

    public static final String BASE_FILE_PREFIX_WITH_PATH = BASE_DATA_DIRECTORY_NAME + BASE_FILE_PREFIX;

    public static final String BASE_FILE_EXTENSION = ".json";
}
