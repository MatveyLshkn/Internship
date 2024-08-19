package lma.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapper<E> {
    E mapFromResultSet(ResultSet resultSet) throws SQLException;

    PreparedStatement mapToPreparedStatement(E entity, PreparedStatement preparedStatement,
                                             boolean forUpdate) throws SQLException;
}
