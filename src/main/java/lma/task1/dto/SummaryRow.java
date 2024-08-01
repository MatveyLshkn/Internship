package lma.task1.dto;

import lma.task1.enums.CreditStatusEnum;
import lma.task1.enums.PeriodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryRow {

    private Long creditId;

    private Long userId;

    private String fullName;

    private Integer transactionCount;

    private BigDecimal debt;

    private PeriodEnum period;

    private CreditStatusEnum creditStatus;

    private LocalDate repaymentDate;
}
