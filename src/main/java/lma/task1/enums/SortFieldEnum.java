package lma.task1.enums;

import lma.task1.exception.WrongEnumNameException;
import lombok.RequiredArgsConstructor;

import static lma.task1.constants.CommonConstants.WRONG_ENUM_NAME_MESSAGE;

@RequiredArgsConstructor
public enum SortFieldEnum {
    NAME("Name"),
    DEBT("Debt");

    private final String sortFieldName;

    public static SortFieldEnum getSortField(String sortFieldName) {
        for (SortFieldEnum sortField : SortFieldEnum.values()) {
            if (sortField.name().equalsIgnoreCase(sortFieldName)) {
                return sortField;
            }
        }
        throw new WrongEnumNameException(WRONG_ENUM_NAME_MESSAGE.format(sortFieldName));
    }

    @Override
    public String toString() {
        return sortFieldName;
    }
}