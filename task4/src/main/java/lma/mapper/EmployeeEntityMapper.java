package lma.mapper;

import lma.constants.EmployeeConstants;
import lma.entity.Employee;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static lma.constants.EmployeeConstants.HIRE_DATE_COLUMN;
import static lma.constants.EmployeeConstants.POSITION_COLUMN;
import static lma.constants.EmployeeConstants.SALARY_COLUMN;

public class EmployeeEntityMapper implements EntityMapper<Employee> {

    @Override
    public Employee mapFromResultSet(ResultSet resultSet) throws SQLException {
        return Employee.builder()
                .id(resultSet.getLong(EmployeeConstants.ID_COLUMN))
                .fullName(resultSet.getString(EmployeeConstants.FULL_NAME_COLUMN))
                .position(resultSet.getString(POSITION_COLUMN))
                .hireDate(resultSet.getDate(HIRE_DATE_COLUMN).toLocalDate())
                .salary(resultSet.getBigDecimal(SALARY_COLUMN))
                .build();
    }

    @Override
    public PreparedStatement mapToPreparedStatement(Employee entity, PreparedStatement preparedStatement,
                                                    boolean forUpdate) throws SQLException {

        preparedStatement.setString(1, entity.getFullName());
        preparedStatement.setString(2, entity.getPosition());
        preparedStatement.setDate(3, Date.valueOf(entity.getHireDate()));
        preparedStatement.setBigDecimal(4, entity.getSalary());
        if (forUpdate) {
            preparedStatement.setLong(5, entity.getId());
        }
        return preparedStatement;
    }
}
