package lma.task1.enums;

import lma.task1.exception.WrongEnumNameException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

import static lma.task1.constants.CommonConstants.WRONG_ENUM_NAME_MESSAGE;

@RequiredArgsConstructor
public enum PeriodEnum {
    DAY("Day"),
    WEEK("Week"),
    MONTH("Month"),
    YEAR("Year");

    private final String periodName;

    public static PeriodEnum getPeriod(String periodName) {
        for (PeriodEnum period : PeriodEnum.values()) {
            if (period.name().equalsIgnoreCase(periodName)) {
                return period;
            }
        }
        throw new WrongEnumNameException(WRONG_ENUM_NAME_MESSAGE.format(periodName));
    }

    public static int getPeriodDays(PeriodEnum period, LocalDate referenceDate) {
        return switch (period) {
            case DAY -> 1;
            case WEEK -> 7;
            case MONTH -> YearMonth.from(referenceDate).lengthOfMonth();
            case YEAR -> YearMonth.from(referenceDate).lengthOfYear();
        };
    }

    @Override
    public String toString() {
        return periodName;
    }
}
