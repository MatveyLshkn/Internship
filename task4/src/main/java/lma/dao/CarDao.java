package lma.dao;

import lma.constants.CommonConstants;
import lma.entity.Car;
import lma.exception.DatabaseException;
import lma.mapper.CarEntityMapper;
import lma.queryFactory.CarSqlQueryFactory;
import lma.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lma.constants.ExceptionConstants.DATABASE_EXCEPTION_MESSAGE;

public class CarDao extends CrudDao<Long, Car> {

    private CarSqlQueryFactory queryFactory;

    public CarDao() {
        super(Car.class, CommonConstants.AVAILABLE_ENTITIES);
        queryFactory = new CarSqlQueryFactory();
    }

    public List<Car> findAllByBrand(String brand) {
        String findAllByBrandQuery = queryFactory.getFindAllByBrandQuery(brand);
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllByBrandQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Car> carList = new ArrayList<>();
            while (resultSet.next()) {
                carList.add(new CarEntityMapper().mapFromResultSet(resultSet));
            }
            return carList;

        } catch (SQLException e) {
            throw new DatabaseException(DATABASE_EXCEPTION_MESSAGE.formatted(e));
        }
    }
}
