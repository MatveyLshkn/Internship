package lma.task1.enums;

import lma.task1.exception.WrongEnumNameException;
import lombok.RequiredArgsConstructor;

import static lma.task1.constants.CommonConstants.WRONG_ENUM_NAME_MESSAGE;

@RequiredArgsConstructor
public enum CreditStatusEnum {
    IN_PROGRESS("In_Progress"),
    DONE("Done");

    private final String creditStatusName;

    public static CreditStatusEnum getCreditStatus(String creditStatusName) {
        for (CreditStatusEnum creditStatus : CreditStatusEnum.values()) {
            if (creditStatus.name().equalsIgnoreCase(creditStatusName)) {
                return creditStatus;
            }
        }
        throw new WrongEnumNameException(WRONG_ENUM_NAME_MESSAGE.format(creditStatusName));
    }

    @Override
    public String toString() {
        return creditStatusName;
    }
}
