package lma.entity.description;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TableQueryInfo {
    private final String tableName;

    private final String setClause;

    private final String modifiableColumns;

    private final String placeholder;
}
