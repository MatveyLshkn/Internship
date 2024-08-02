package lma.service;

import lma.constants.CommonConstants;
import lma.constants.ExceptionConstants;
import lma.dao.CarDao;
import lma.entity.Car;

import java.util.List;

public class CarService extends CrudService<Long, Car> {

    private CarDao carDao;

    public CarService() {
        super(Car.class);
        carDao = new CarDao();
    }

    public List<Car> findAllByBrand(String brand) {
        return carDao.findAllByBrand(brand);
    }
}
