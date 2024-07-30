package lma.task1.enums;

import lma.task1.exception.WrongEnumNameException;
import lombok.RequiredArgsConstructor;

import static lma.task1.constants.CommonConstants.WRONG_ENUM_NAME_MESSAGE;

@RequiredArgsConstructor
public enum SexEnum {
    MALE("Male"),
    FEMALE("Female"),
    ANY("Any");

    private final String sexName;

    public static SexEnum getSex(String sexName) {
        for (SexEnum sex : SexEnum.values()) {
            if (sex.name().equalsIgnoreCase(sexName)) {
                return sex;
            }
        }
        throw new WrongEnumNameException(WRONG_ENUM_NAME_MESSAGE.format(sexName));
    }

    @Override
    public String toString() {
        return sexName;
    }
}
