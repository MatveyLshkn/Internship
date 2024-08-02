package lma.constants;

import lma.entity.description.TableQueryInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CarConstants {

    public static final String TABLE_NAME = "car";

    public static final String SET_CLAUSE = """
            model_id = ?,
            color = ?,
            production_date = ?,
            price = ?,
            mileage = ?
            """;

    public static final String MODIFIABLE_COLUMNS = "model_id, color, production_date, price, mileage";

    public static final String PLACEHOLDERS = "?, ?, ?, ?, ?";

    public static final TableQueryInfo TABLE_INFO = new TableQueryInfo(TABLE_NAME, SET_CLAUSE, MODIFIABLE_COLUMNS, PLACEHOLDERS);

    public static final String ID_COLUMN = "id";

    public static final String MODEL_ID_COLUMN = "model_id";

    public static final String COLOR_COLUMN = "color";

    public static final String PRODUCTION_DATE_COLUMN = "production_date";

    public static final String PRICE_COLUMN = "price";

    public static final String MILEAGE_COLUMN = "mileage";

    public static final String FIND_ALL_BY_CAR_BRAND_SQL = """
                SELECT car.id, car.model_id, car.color, car.production_date, car.price, car.mileage
                FROM car
                JOIN model ON car.model_id = model.id
                WHERE model.brand = '%s';
                """;
}
