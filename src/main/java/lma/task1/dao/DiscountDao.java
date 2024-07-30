package lma.task1.dao;

import lma.task1.loader.DataLoader;
import lma.task1.entity.Discount;

import java.util.List;

public class DiscountDao {
    private static final DiscountDao INSTANCE = new DiscountDao();

    private DiscountDao() {
    }

    public static DiscountDao getInstance() {
        return INSTANCE;
    }

    public List<Discount> findAll() {
        return DataLoader.getFileData().getDiscounts();
    }
}
