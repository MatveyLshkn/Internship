package lma.entity.description;

import lma.constants.DiscountConstants;
import lma.entity.Discount;
import lma.mapper.DiscountEntityMapper;
import lma.mapper.EntityMapper;

import static lma.constants.DiscountConstants.TABLE_INFO;

public class DiscountEntityDescription implements EntityDescription<Discount>{
    @Override
    public Class<Discount> getEntityClass() {
        return Discount.class;
    }

    @Override
    public TableQueryInfo getTableQueryInfo() {
        return TABLE_INFO;
    }

    @Override
    public EntityMapper getEntityMapper() {
        return new DiscountEntityMapper();
    }
}
