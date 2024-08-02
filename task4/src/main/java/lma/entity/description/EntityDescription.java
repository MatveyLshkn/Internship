package lma.entity.description;

import lma.mapper.EntityMapper;

public interface EntityDescription<E> {

    Class<E> getEntityClass();

    TableQueryInfo getTableQueryInfo();

    EntityMapper getEntityMapper();
}
