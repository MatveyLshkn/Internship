package lma.enums;

import lma.exception.WrongEnumNameException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static lma.constants.ExceptionConstants.WRONG_ENUM_NAME_EXCEPTION_MESSAGE;

@RequiredArgsConstructor
public enum CarTypeEnum {
    SEDAN("Sedan"),
    SUV("SUV"),
    TRUCK("Truck"),
    CONVERTIBLE("Convertible"),
    HATCHBACK("Hatchback"),
    SPORT("Sport"),
    HYPER("Hyper");

    private final String carTypeName;

    public static CarTypeEnum getCarType(String carTypeName) {
        return Arrays.stream(CarTypeEnum.values())
                .filter(type -> type.carTypeName.equalsIgnoreCase(carTypeName))
                .findFirst()
                .orElseThrow(() -> new WrongEnumNameException(WRONG_ENUM_NAME_EXCEPTION_MESSAGE.formatted(carTypeName)));
    }

    @Override
    public String toString() {
        return carTypeName;
    }
}
