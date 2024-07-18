package lma.task1.util;

import lma.task1.dto.SummaryRow;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PrinterUtil {
    public void printWholeTable(List<SummaryRow> summaryRows) {
        System.out.println(FormatterUtil.getHeader());
        for(SummaryRow summaryRow : summaryRows) {
            System.out.println(FormatterUtil.formatSummaryRow(summaryRow));
        }
    }
}
