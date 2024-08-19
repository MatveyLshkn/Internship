package lma.entity.description;

import lma.constants.CustomerConstants;
import lma.entity.Customer;
import lma.mapper.CustomerEntityMapper;
import lma.mapper.EntityMapper;

import static lma.constants.CustomerConstants.TABLE_INFO;

public class CustomerEntityDescription implements EntityDescription<Customer>{
    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }

    @Override
    public TableQueryInfo getTableQueryInfo() {
        return TABLE_INFO;
    }

    @Override
    public EntityMapper getEntityMapper() {
        return new CustomerEntityMapper();
    }
}
