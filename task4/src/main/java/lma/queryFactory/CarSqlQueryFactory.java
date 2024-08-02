package lma.queryFactory;

import lma.constants.CarConstants;

import static lma.constants.CarConstants.FIND_ALL_BY_CAR_BRAND_SQL;

public class CarSqlQueryFactory extends SqlQueryFactory {

    public CarSqlQueryFactory() {
        super(CarConstants.TABLE_INFO);
    }

    public String getFindAllByBrandQuery(String brand){
        return FIND_ALL_BY_CAR_BRAND_SQL.formatted(brand);
    }
}
