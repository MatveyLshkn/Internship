package lma.constants;

import lma.entity.description.TableQueryInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DiscountConstants {

    public static final String TABLE_NAME = "discount";

    public static final String SET_CLAUSE = """
            percentage = ?,
            start_date = ?,
            end_date = ?,
            model_id = ?
            """;

    public static final String MODIFIABLE_COLUMNS = "percentage, start_date, end_date, model_id";

    public static final String PLACEHOLDERS = "?, ?, ?, ?";

    public static final String ID_COLUMN = "id";

    public static final TableQueryInfo TABLE_INFO = new TableQueryInfo(TABLE_NAME, SET_CLAUSE, MODIFIABLE_COLUMNS, PLACEHOLDERS);

    public static final String PERCENTAGE_COLUMN = "percentage";

    public static final String START_DATE_COLUMN = "start_date";

    public static final String END_DATE_COLUMN = "end_date";

    public static final String MODEL_ID_COLUMN = "model_id";
}
