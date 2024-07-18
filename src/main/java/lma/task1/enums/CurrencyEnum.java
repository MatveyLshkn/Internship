package lma.task1.enums;

import lma.task1.exception.WrongEnumNameException;
import lombok.RequiredArgsConstructor;

import static lma.task1.constants.CommonConstants.WRONG_ENUM_NAME_MESSAGE;

@RequiredArgsConstructor
public enum CurrencyEnum {
    EUR("Eur"),
    USD("Usd"),
    BYN("Byn");

    private final String currencyName;

    public static CurrencyEnum getCurrency(String currencyName) {
        for (CurrencyEnum currency : CurrencyEnum.values()) {
            if (currency.name().equalsIgnoreCase(currencyName)) {
                return currency;
            }
        }
        throw new WrongEnumNameException(WRONG_ENUM_NAME_MESSAGE.format(currencyName));
    }

    @Override
    public String toString() {
        return currencyName;
    }
}
