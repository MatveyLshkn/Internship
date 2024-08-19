package lma.constants;

import lma.entity.description.TableQueryInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeConstants {

    public static final String TABLE_NAME = "employee";

    public static final String SET_CLAUSE = """
            full_name = ?,
            position = ?,
            hire_date = ?,
            salary = ?
            """;

    public static final String MODIFIABLE_COLUMNS = "full_name, position, hire_date, salary";

    public static final String PLACEHOLDERS = "?, ?, ?, ?";

    public static final String ID_COLUMN = "id";

    public static final TableQueryInfo TABLE_INFO = new TableQueryInfo(TABLE_NAME, SET_CLAUSE, MODIFIABLE_COLUMNS, PLACEHOLDERS);

    public static final String FULL_NAME_COLUMN = "full_name";

    public static final String POSITION_COLUMN = "position";

    public static final String HIRE_DATE_COLUMN = "hire_date";

    public static final String SALARY_COLUMN = "salary";
}
