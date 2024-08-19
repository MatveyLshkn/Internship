package lma.mapper;

import lma.constants.CustomerConstants;
import lma.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static lma.constants.CustomerConstants.ADDRESS_COLUMN;
import static lma.constants.CustomerConstants.EMAIL_COLUMN;
import static lma.constants.CustomerConstants.FULL_NAME_COLUMN;
import static lma.constants.CustomerConstants.PHONE_NUMBER_COLUMN;

public class CustomerEntityMapper implements EntityMapper<Customer> {

    @Override
    public Customer mapFromResultSet(ResultSet resultSet) throws SQLException {
        return Customer.builder()
                .id(resultSet.getLong(CustomerConstants.ID_COLUMN))
                .fullName(resultSet.getString(FULL_NAME_COLUMN))
                .email(resultSet.getString(EMAIL_COLUMN))
                .phoneNumber(resultSet.getString(PHONE_NUMBER_COLUMN))
                .address(resultSet.getString(ADDRESS_COLUMN))
                .build();
    }

    @Override
    public PreparedStatement mapToPreparedStatement(Customer entity, PreparedStatement preparedStatement,
                                                    boolean forUpdate) throws SQLException {

        preparedStatement.setString(1, entity.getFullName());
        preparedStatement.setString(2, entity.getEmail());
        preparedStatement.setString(3, entity.getPhoneNumber());
        preparedStatement.setString(4, entity.getAddress());
        if (forUpdate) {
            preparedStatement.setLong(5, entity.getId());
        }
        return preparedStatement;
    }
}
