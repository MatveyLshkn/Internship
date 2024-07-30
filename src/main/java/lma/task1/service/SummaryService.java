package lma.task1.service;

import lma.task1.entity.Credit;
import lma.task1.entity.Settings;
import lma.task1.entity.ShowFor;
import lma.task1.entity.Transaction;
import lma.task1.entity.User;
import lma.task1.loader.SettingsLoader;
import lma.task1.enums.CreditStatusEnum;
import lma.task1.dto.SummaryRow;
import lma.task1.enums.ShowTypeEnum;
import lma.task1.enums.SortFieldEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class SummaryService {

    private static final SummaryService INSTANCE = new SummaryService();

    private final UserService userService;

    private final CreditService creditService;

    private final DiscountService discountService;

    private final TransactionService transactionService;

    private final EventService eventService;

    private final Settings settings;

    private final CreditCalculator creditCalculator;

    private SummaryService() {
        userService = UserService.getInstance();
        creditService = CreditService.getInstance();
        discountService = DiscountService.getInstance();
        transactionService = TransactionService.getInstance();
        eventService = EventService.getInstance();
        settings = SettingsLoader.getSettings();
        creditCalculator = CreditCalculator.getInstance();
    }

    public static SummaryService getInstance() {
        return INSTANCE;
    }

    public List<SummaryRow> getSummary() {
        List<SummaryRow> summaryRows = new ArrayList<>();

        LocalDate dateFrom = settings.getDateFrom();
        LocalDate dateTo = settings.getDateTo();

        List<Credit> credits = creditService.getCreditsByDate(dateFrom, dateTo);
        processCredits(summaryRows, credits, dateFrom, dateTo);

        processSummaryRows(summaryRows);
        return summaryRows;
    }

    private void processSummaryRows(List<SummaryRow> summaryRows) {
        filterSummaryRows(summaryRows);
        sortSummaryRows(summaryRows);
    }

    private void filterSummaryRows(List<SummaryRow> summaryRows) {
        ShowFor showFor = settings.getShowFor();
        if (showFor == null) return;

        List<Object> users = showFor.getUsers();
        Predicate<SummaryRow> filterPredicate = showFor.getType() == ShowTypeEnum.ID
                ? row -> users.contains((double) row.getUserId())
                : row -> users.contains(row.getFullName());

        summaryRows.removeIf(filterPredicate.negate());
    }

    private void sortSummaryRows(List<SummaryRow> summaryRows) {
        SortFieldEnum sortBy = settings.getSortBy();
        Comparator<SummaryRow> comparator = sortBy.equals(SortFieldEnum.DEBT)
                ? Comparator.comparing(SummaryRow::getDebt)
                : Comparator.comparing(SummaryRow::getFullName);

        summaryRows.sort(comparator);
    }

    private void processCredits(List<SummaryRow> summaryRows, List<Credit> credits,
                                LocalDate dateFrom, LocalDate dateTo) {
        for (Credit credit : credits) {
            SummaryRow summaryRow = createSummaryRow(credit, dateFrom, dateTo);
            summaryRows.add(summaryRow);
        }
    }

    private SummaryRow createSummaryRow(Credit credit, LocalDate dateFrom, LocalDate dateTo) {
        SummaryRow summaryRow = new SummaryRow();
        fillUserDetails(summaryRow, credit.getUserId());

        List<Transaction> transactions = transactionService.filterTransactionByCreditAndDate(credit, dateFrom, dateTo);
        fillSummaryRow(summaryRow, credit, transactions, dateFrom, dateTo);

        creditCalculator.calculateDebtAndRepayment(summaryRow, credit, transactions);

        return summaryRow;
    }

    private void fillUserDetails(SummaryRow summaryRow, Long userId) {
        User user = userService.getUserById(userId);
        summaryRow.setUserId(user.getId());
        summaryRow.setFullName(user.getName() + " " + user.getSecondName());
    }

    private void fillSummaryRow(SummaryRow summaryRow, Credit credit,
                                List<Transaction> transactions, LocalDate dateFrom, LocalDate dateTo) {
        summaryRow.setCreditId(credit.getId());
        summaryRow.setPeriod(credit.getPeriod());
        summaryRow.setTransactionCount(transactions.size());
        summaryRow.setCreditStatus(CreditStatusEnum.IN_PROGRESS);
    }
}