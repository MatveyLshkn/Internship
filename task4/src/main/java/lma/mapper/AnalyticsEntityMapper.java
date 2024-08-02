package lma.mapper;

import lma.entity.Analytics;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static lma.constants.AnalyticsConstants.CAR_ID_COLUMN;
import static lma.constants.AnalyticsConstants.CUSTOMER_ID_COLUMN;
import static lma.constants.AnalyticsConstants.DATE_COLUMN;
import static lma.constants.AnalyticsConstants.DISCOUNT_ID_COLUMN;
import static lma.constants.AnalyticsConstants.ID_COLUMN;
import static lma.constants.AnalyticsConstants.SALE_ASSISTANT_ID_COLUMN;
import static lma.constants.AnalyticsConstants.SOLD_PRICE_COLUMN;

public class AnalyticsEntityMapper implements EntityMapper<Analytics> {

    @Override
    public Analytics mapFromResultSet(ResultSet from) throws SQLException {
        return Analytics.builder()
                .id(from.getLong(ID_COLUMN))
                .carId(from.getLong(CAR_ID_COLUMN))
                .customerId(from.getLong(CUSTOMER_ID_COLUMN))
                .date(from.getDate(DATE_COLUMN).toLocalDate())
                .saleAssistantId(from.getLong(SALE_ASSISTANT_ID_COLUMN))
                .soldPrice(from.getBigDecimal(SOLD_PRICE_COLUMN))
                .discountId(from.getLong(DISCOUNT_ID_COLUMN))
                .build();
    }

    @Override
    public PreparedStatement mapToPreparedStatement(Analytics entity, PreparedStatement preparedStatement,
                                                    boolean forUpdate) throws SQLException {

        preparedStatement.setLong(1, entity.getCarId());
        preparedStatement.setLong(2, entity.getCustomerId());
        preparedStatement.setDate(3, Date.valueOf(entity.getDate()));
        preparedStatement.setLong(4, entity.getSaleAssistantId());
        preparedStatement.setBigDecimal(5, entity.getSoldPrice());
        preparedStatement.setLong(6, entity.getDiscountId());
        if (forUpdate) {
            preparedStatement.setLong(7, entity.getId());
        }
        return preparedStatement;
    }
}
