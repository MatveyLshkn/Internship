package lma.mapper;

import lma.constants.ModelConstants;
import lma.entity.Model;
import lma.enums.CarTypeEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static lma.constants.ModelConstants.BRAND_COLUMN;
import static lma.constants.ModelConstants.CAR_TYPE_COLUMN;
import static lma.constants.ModelConstants.MODEL_NAME_COLUMN;

public class ModelEntityMapper implements EntityMapper<Model> {

    @Override
    public Model mapFromResultSet(ResultSet resultSet) throws SQLException {
        return Model.builder()
                .id(resultSet.getLong(ModelConstants.ID_COLUMN))
                .carType(CarTypeEnum.getCarType(resultSet.getString(CAR_TYPE_COLUMN)))
                .brand(resultSet.getString(BRAND_COLUMN))
                .modelName(resultSet.getString(MODEL_NAME_COLUMN))
                .build();
    }

    @Override
    public PreparedStatement mapToPreparedStatement(Model entity, PreparedStatement preparedStatement,
                                                    boolean forUpdate) throws SQLException {

        preparedStatement.setString(1, entity.getCarType().name());
        preparedStatement.setString(2, entity.getBrand());
        preparedStatement.setString(3, entity.getModelName());
        if (forUpdate) {
            preparedStatement.setLong(4, entity.getId());
        }
        return preparedStatement;
    }
}
