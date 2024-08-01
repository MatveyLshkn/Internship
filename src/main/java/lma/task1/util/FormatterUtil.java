package lma.task1.util;

import lma.task1.dto.SummaryRow;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FormatterUtil {
    private final String HEADER = String.format("%-10s  | %-10s | %-20s | %-25s | %-15s | %-15s | %-15s | %-25s\n",
            "CreditId", "UserId", "FullName", "TransactionCount",
            "Debt", "Period", "Status", "RepaymentDate");


    public String formatSummaryRow(SummaryRow summaryRow) {
        return """
                 %-10d | %-10d | %-20s | %-25d | %-15.2f | %-15s | %-15s | %-15s\n
                """.formatted(summaryRow.getCreditId(), summaryRow.getUserId(),
                summaryRow.getFullName(), summaryRow.getTransactionCount(),
                summaryRow.getDebt(), summaryRow.getPeriod().name(),
                summaryRow.getCreditStatus().name(),
                summaryRow.getRepaymentDate() == null ? " " : summaryRow.getRepaymentDate());
    }

    public String getHeader() {
        return HEADER;
    }
}
