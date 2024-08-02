package lma.entity.description;

import lma.constants.ModelConstants;
import lma.entity.Model;
import lma.mapper.EntityMapper;
import lma.mapper.ModelEntityMapper;

import static lma.constants.ModelConstants.TABLE_INFO;

public class ModelEntityDescription implements EntityDescription<Model>{

    @Override
    public Class<Model> getEntityClass() {
        return Model.class;
    }

    @Override
    public TableQueryInfo getTableQueryInfo() {
        return TABLE_INFO;
    }

    @Override
    public EntityMapper getEntityMapper() {
        return new ModelEntityMapper();
    }
}
