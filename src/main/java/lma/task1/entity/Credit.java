package lma.task1.entity;

import lma.task1.enums.PeriodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    private Long id;

    private Long userId;

    private LocalDate date;

    private PeriodEnum period;

    private BigDecimal money;

    private Double rate;
}