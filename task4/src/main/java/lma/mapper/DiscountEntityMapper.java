package lma.mapper;

import lma.constants.DiscountConstants;
import lma.entity.Discount;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static lma.constants.DiscountConstants.END_DATE_COLUMN;
import static lma.constants.DiscountConstants.PERCENTAGE_COLUMN;
import static lma.constants.DiscountConstants.START_DATE_COLUMN;

public class DiscountEntityMapper implements EntityMapper<Discount> {

    @Override
    public Discount mapFromResultSet(ResultSet resultSet) throws SQLException {
        return Discount.builder()
                .id(resultSet.getLong(DiscountConstants.ID_COLUMN))
                .percentage(resultSet.getDouble(PERCENTAGE_COLUMN))
                .startDate(resultSet.getDate(START_DATE_COLUMN).toLocalDate())
                .endDate(resultSet.getDate(END_DATE_COLUMN).toLocalDate())
                .modelId(resultSet.getLong(DiscountConstants.MODEL_ID_COLUMN))
                .build();
    }

    @Override
    public PreparedStatement mapToPreparedStatement(Discount entity, PreparedStatement preparedStatement,
                                                    boolean forUpdate) throws SQLException {
        preparedStatement.setDouble(1, entity.getPercentage());
        preparedStatement.setDate(2, Date.valueOf(entity.getStartDate()));
        preparedStatement.setDate(3, Date.valueOf(entity.getEndDate()));
        preparedStatement.setLong(4, entity.getModelId());
        if (forUpdate) {
            preparedStatement.setLong(5, entity.getId());
        }
        return preparedStatement;
    }
}
