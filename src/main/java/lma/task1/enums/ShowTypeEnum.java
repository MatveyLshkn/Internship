package lma.task1.enums;

import lma.task1.exception.WrongEnumNameException;
import lombok.RequiredArgsConstructor;

import static lma.task1.constants.CommonConstants.WRONG_ENUM_NAME_MESSAGE;

@RequiredArgsConstructor
public enum ShowTypeEnum {
    ID("Id"),
    NAME("Name");

    private final String showTypeName;

    public static ShowTypeEnum getShowType(String showTypeName) {
        for (ShowTypeEnum showType : ShowTypeEnum.values()) {
            if (showType.name().equalsIgnoreCase(showTypeName)) {
                return showType;
            }
        }
        throw new WrongEnumNameException(WRONG_ENUM_NAME_MESSAGE.format(showTypeName));
    }

    @Override
    public String toString() {
        return showTypeName;
    }
}
