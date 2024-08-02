package lma.entity.description;

import lma.constants.EmployeeConstants;
import lma.entity.Employee;
import lma.mapper.EmployeeEntityMapper;
import lma.mapper.EntityMapper;

import static lma.constants.EmployeeConstants.TABLE_INFO;

public class EmployeeEntityDescription implements EntityDescription<Employee> {
    @Override
    public Class<Employee> getEntityClass() {
        return Employee.class;
    }

    @Override
    public TableQueryInfo getTableQueryInfo() {
        return TABLE_INFO;
    }

    @Override
    public EntityMapper getEntityMapper() {
        return new EmployeeEntityMapper();
    }
}
