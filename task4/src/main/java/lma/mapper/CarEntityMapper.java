package lma.mapper;

import lma.constants.CarConstants;
import lma.entity.Car;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static lma.constants.CarConstants.COLOR_COLUMN;
import static lma.constants.CarConstants.MILEAGE_COLUMN;
import static lma.constants.CarConstants.MODEL_ID_COLUMN;
import static lma.constants.CarConstants.PRICE_COLUMN;
import static lma.constants.CarConstants.PRODUCTION_DATE_COLUMN;

public class CarEntityMapper implements EntityMapper<Car> {

    @Override
    public Car mapFromResultSet(ResultSet resultSet) throws SQLException {
        return Car.builder()
                .id(resultSet.getLong(CarConstants.ID_COLUMN))
                .modelId(resultSet.getLong(MODEL_ID_COLUMN))
                .color(resultSet.getString(COLOR_COLUMN))
                .productionDate(resultSet.getDate(PRODUCTION_DATE_COLUMN).toLocalDate())
                .price(resultSet.getBigDecimal(PRICE_COLUMN))
                .mileage(resultSet.getLong(MILEAGE_COLUMN))
                .build();
    }

    @Override
    public PreparedStatement mapToPreparedStatement(Car entity, PreparedStatement preparedStatement,
                                                    boolean forUpdate) throws SQLException {

        preparedStatement.setLong(1, entity.getModelId());
        preparedStatement.setString(2, entity.getColor());
        preparedStatement.setDate(3, Date.valueOf(entity.getProductionDate()));
        preparedStatement.setBigDecimal(4, entity.getPrice());
        preparedStatement.setLong(5, entity.getMileage());
        if (forUpdate) {
            preparedStatement.setLong(6, entity.getId());
        }
        return preparedStatement;
    }
}
