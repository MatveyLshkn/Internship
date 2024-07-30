package lma.task1.entity;

import lma.task1.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Long id;

    private CurrencyEnum currency;

    private BigDecimal cost;

    private LocalDate date;
}