package lma.entity.description;

import lma.constants.AnalyticsConstants;
import lma.entity.Analytics;
import lma.mapper.AnalyticsEntityMapper;
import lma.mapper.EntityMapper;

import static lma.constants.AnalyticsConstants.TABLE_INFO;

public class AnalyticsEntityDescription implements EntityDescription<Analytics>{
    @Override
    public Class<Analytics> getEntityClass() {
        return Analytics.class;
    }

    @Override
    public TableQueryInfo getTableQueryInfo() {
        return TABLE_INFO;
    }

    @Override
    public EntityMapper getEntityMapper() {
        return new AnalyticsEntityMapper();
    }
}
