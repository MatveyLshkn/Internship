package lma.task1.enums;

import lma.task1.exception.WrongEnumNameException;
import lombok.RequiredArgsConstructor;

import static lma.task1.constants.CommonConstants.WRONG_ENUM_NAME_MESSAGE;

@RequiredArgsConstructor
public enum DiscountTypeEnum {
    ONE("One"),
    MANY("Many");

    private final String discountTypeName;

    public static DiscountTypeEnum getDiscountType(String discountTypeName) {
        for (DiscountTypeEnum discountType : DiscountTypeEnum.values()) {
            if (discountType.name().equalsIgnoreCase(discountTypeName)) {
                return discountType;
            }
        }
        throw new WrongEnumNameException(WRONG_ENUM_NAME_MESSAGE.format(discountTypeName));
    }

    @Override
    public String toString() {
        return discountTypeName;
    }
}
