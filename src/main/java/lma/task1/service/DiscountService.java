package lma.task1.service;

import lma.task1.dao.DiscountDao;
import lma.task1.entity.Discount;
import lma.task1.enums.DiscountTypeEnum;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DiscountService {
    private static final DiscountService INSTANCE = new DiscountService();
    private final DiscountDao discountDao = DiscountDao.getInstance();

    private DiscountService() {
    }

    public static DiscountService getInstance() {
        return INSTANCE;
    }

    public List<Discount> findAll(){
        return discountDao.findAll().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Discount> getOneDayDiscounts(){
        return findAll().stream()
                .filter(discount -> discount.getType().equals(DiscountTypeEnum.ONE))
                .toList();
    }

    public List<Discount> getPeriodDiscounts(){
        return findAll().stream()
                .filter(discount -> discount.getType().equals(DiscountTypeEnum.MANY))
                .toList();
    }
}
