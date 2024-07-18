package lma.task1.entity;

import lma.task1.enums.DiscountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discount {

    private Long id;

    private Double discount;

    private DiscountTypeEnum type;

    private LocalDate date;

    private LocalDate dateFrom;

    private LocalDate dateTo;
}