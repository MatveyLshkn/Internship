package lma.constants;

import lma.entity.description.TableQueryInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AnalyticsConstants {

    public static final String TABLE_NAME = "analytics";

    public static final String SET_CLAUSE = """
            car_id = ?,
            customer_id = ?,
            sale_date = ?,
            sale_assistant_id = ?,
            sold_price = ?,
            discount_id = ?
            """;

    public static final String MODIFIABLE_COLUMNS = "car_id, customer_id, sale_date, sale_assistant_id, sold_price, discount_id";

    public static final String PLACEHOLDERS = "?, ?, ?, ?, ?, ?";

    public static final TableQueryInfo TABLE_INFO = new TableQueryInfo(TABLE_NAME, SET_CLAUSE, MODIFIABLE_COLUMNS, PLACEHOLDERS);

    public static final String ID_COLUMN = "id";

    public static final String CAR_ID_COLUMN = "car_id";

    public static final String CUSTOMER_ID_COLUMN = "customer_id";

    public static final String DATE_COLUMN = "sale_date";

    public static final String SALE_ASSISTANT_ID_COLUMN = "sale_assistant_id";

    public static final String SOLD_PRICE_COLUMN = "sold_price";

    public static final String DISCOUNT_ID_COLUMN = "discount_id";
}
