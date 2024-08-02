package lma.entity.description;

import lma.constants.CarConstants;
import lma.entity.Car;
import lma.mapper.CarEntityMapper;
import lma.mapper.EntityMapper;

import static lma.constants.CarConstants.TABLE_INFO;

public class CarEntityDescription implements EntityDescription<Car>{
    @Override
    public Class<Car> getEntityClass() {
        return Car.class;
    }

    @Override
    public TableQueryInfo getTableQueryInfo() {
        return TABLE_INFO;
    }

    @Override
    public EntityMapper getEntityMapper() {
        return new CarEntityMapper();
    }
}
