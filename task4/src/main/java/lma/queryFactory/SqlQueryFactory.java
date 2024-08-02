package lma.queryFactory;

import lma.entity.description.TableQueryInfo;

import static lma.constants.SqlQueryConstants.DELETE_BY_ID_TEMPLATE;
import static lma.constants.SqlQueryConstants.FIND_ALL_TEMPLATE;
import static lma.constants.SqlQueryConstants.FIND_BY_ID_TEMPLATE;
import static lma.constants.SqlQueryConstants.IS_PRESENT_QUERY;
import static lma.constants.SqlQueryConstants.SAVE_TEMPLATE;
import static lma.constants.SqlQueryConstants.UPDATE_BY_ID_TEMPLATE;


public class SqlQueryFactory {

    private final TableQueryInfo tableQueryInfo;

    public SqlQueryFactory(TableQueryInfo tableQueryInfo) {
        this.tableQueryInfo = tableQueryInfo;
    }

    public String getIsPresentQuery() {
        return IS_PRESENT_QUERY.formatted(tableQueryInfo.getTableName());
    }

    public String getFindByIdQuery() {
        return FIND_BY_ID_TEMPLATE.formatted(tableQueryInfo.getTableName());
    }

    public String getDeleteByIdQuery() {
        return DELETE_BY_ID_TEMPLATE.formatted(tableQueryInfo.getTableName());
    }

    public String getUpdateByIdQuery() {
        return UPDATE_BY_ID_TEMPLATE.formatted(tableQueryInfo.getTableName(), tableQueryInfo.getSetClause());
    }

    public String getSaveQuery() {
        return SAVE_TEMPLATE.formatted(tableQueryInfo.getTableName(),
                tableQueryInfo.getModifiableColumns(), tableQueryInfo.getPlaceholder());
    }

    public String getFindAllQuery() {
        return FIND_ALL_TEMPLATE.formatted(tableQueryInfo.getTableName());
    }
}
