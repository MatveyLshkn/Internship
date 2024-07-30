package lma.task1;

import lma.task1.dto.SummaryRow;
import lma.task1.util.PrinterUtil;
import lma.task1.service.SummaryService;

import java.util.List;

public class ApplicationStarter {
    public static void main(String[] args) {
        List<SummaryRow> summary = SummaryService.getInstance().getSummary();
        PrinterUtil.printWholeTable(summary);
    }
}
