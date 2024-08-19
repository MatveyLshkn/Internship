package lma.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlQueryConstants {
    public static final String IS_PRESENT_QUERY = """
            SELECT EXISTS (
                SELECT *
                FROM %s
                WHERE id = ?
            );
            """;

    public static final String FIND_BY_ID_TEMPLATE = """
            SELECT * 
            FROM %s 
            WHERE id = ?;
            """;

    public static final String DELETE_BY_ID_TEMPLATE = """
            DELETE FROM %s 
            WHERE id = ?
            """;

    public static final String UPDATE_BY_ID_TEMPLATE = """
            UPDATE %s 
            SET %s 
            WHERE id = ?
            """;

    public static final String SAVE_TEMPLATE = """
            INSERT INTO %s (%s) 
            VALUES (%s)
            RETURNING id;
            """;

    public static final String FIND_ALL_TEMPLATE = """
            SELECT * 
            FROM %s;
            """;
}
