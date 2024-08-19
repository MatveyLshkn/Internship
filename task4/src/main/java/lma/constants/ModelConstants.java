package lma.constants;

import lma.entity.description.TableQueryInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ModelConstants {

    public static final String TABLE_NAME = "model";

    public static final String SET_CLAUSE = """
            car_type = ?,
            brand = ?,
            model_name = ?
            """;

    public static final String MODIFIABLE_COLUMNS = "car_type, brand, model_name";

    public static final String PLACEHOLDERS = "?, ?, ?";

    public static final String ID_COLUMN = "id";

    public static final TableQueryInfo TABLE_INFO = new TableQueryInfo(TABLE_NAME, SET_CLAUSE, MODIFIABLE_COLUMNS, PLACEHOLDERS);

    public static final String CAR_TYPE_COLUMN = "car_type";

    public static final String BRAND_COLUMN = "brand";

    public static final String MODEL_NAME_COLUMN = "model_name";
}
