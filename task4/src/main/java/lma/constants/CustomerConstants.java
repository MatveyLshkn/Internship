package lma.constants;

import lma.entity.description.TableQueryInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerConstants {

    public static final String TABLE_NAME = "customer";

    public static final String SET_CLAUSE = """
            full_name = ?,
            email = ?,
            phone_number = ?,
            address = ?
            """;

    public static final String MODIFIABLE_COLUMNS = "full_name, email, phone_number, address";

    public static final String PLACEHOLDERS = "?, ?, ?, ?";

    public static final TableQueryInfo TABLE_INFO = new TableQueryInfo(TABLE_NAME, SET_CLAUSE, MODIFIABLE_COLUMNS, PLACEHOLDERS);

    public static final String ID_COLUMN = "id";

    public static final String FULL_NAME_COLUMN = "full_name";

    public static final String EMAIL_COLUMN = "email";

    public static final String PHONE_NUMBER_COLUMN = "phone_number";

    public static final String ADDRESS_COLUMN = "address";
}
