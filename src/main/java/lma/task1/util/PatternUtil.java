package lma.task1.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class PatternUtil {
    public static final Pattern DEPARTMENT_FILE_PATTERN = Pattern.compile("^db_.*\\.json$");

    public static boolean matchesDepartmentDataName(String dataName) {
        return DEPARTMENT_FILE_PATTERN.matcher(dataName).matches();
    }
}
