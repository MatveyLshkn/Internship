package lma.task1.service;

import lma.task1.entity.Credit;
import lma.task1.entity.Discount;
import lma.task1.entity.Settings;
import lma.task1.entity.Transaction;
import lma.task1.entity.Event;
import lma.task1.loader.SettingsLoader;
import lma.task1.enums.CreditStatusEnum;
import lma.task1.dto.SummaryRow;
import lma.task1.enums.CurrencyEnum;
import lma.task1.enums.PeriodEnum;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static lma.task1.constants.CommonConstants.NO_MULTIPLIER_VALUE;

public class CreditCalculator {

    private static final CreditCalculator INSTANCE = new CreditCalculator();

    private final DiscountService discountService;

    private final EventService eventService;

    private final Settings settings;

    private CreditCalculator() {
        this.discountService = DiscountService.getInstance();
        this.eventService = EventService.getInstance();
        this.settings = SettingsLoader.getSettings();
    }

    public static CreditCalculator getInstance() {
        return INSTANCE;
    }

    void calculateDebtAndRepayment(SummaryRow summaryRow, Credit credit, List<Transaction> transactions) {
        LocalDate creditDate = credit.getDate();
        double rate = credit.getRate();
        double debt = credit.getMoney();

        int period = PeriodEnum.getPeriodDays(credit.getPeriod(), creditDate);

        List<Discount> oneDayDiscounts = discountService.getOneDayDiscounts();
        List<Discount> periodDiscounts = discountService.getPeriodDiscounts();

        transactions = new ArrayList<>(transactions);
        transactions.sort(Comparator.comparing(Transaction::getDate));

        for (Transaction transaction : transactions) {
            double adjustedRate = adjustRateForDiscounts(rate, transaction.getDate(), oneDayDiscounts, periodDiscounts);
            double transactionMoney = adjustMoneyForEvents(transaction);

            debt = calculateNewDebt(debt, adjustedRate, creditDate, transaction.getDate(), period, transactionMoney);


            if (debt <= 0) {
                summaryRow.setDebt(0.0);
                summaryRow.setCreditStatus(CreditStatusEnum.DONE);
                summaryRow.setRepaymentDate(transaction.getDate());
                return;
            }
        }

        summaryRow.setDebt(debt);
    }

    private double adjustRateForDiscounts(double rate, LocalDate date, List<Discount> oneDayDiscounts,
                                          List<Discount> periodDiscounts) {

        List<Discount> applicableDiscounts = oneDayDiscounts.stream()
                .filter(discount -> discount.getDate().equals(date))
                .collect(Collectors.toList());

        applicableDiscounts.addAll(periodDiscounts.stream()
                .filter(discount -> !date.isBefore(discount.getDateFrom()) && !date.isAfter(discount.getDateTo()))
                .collect(Collectors.toList()));

        double adjustedRate = applicableDiscounts.stream()
                .map(Discount::getDiscount)
                .reduce(rate, (finalRate, discount) -> finalRate = finalRate - discount);

        return Math.max(adjustedRate, 0);
    }

    private double adjustMoneyForEvents(Transaction transaction) {
        LocalDate date = transaction.getDate();
        CurrencyEnum currency = transaction.getCurrency();
        double money = transaction.getMoney();

        double multiplier = eventService.getAll().stream()
                .filter(ev -> ev.getDate() != null
                              && !ev.getDate().isAfter(date)
                              && ev.getCurrency().equals(currency))
                .max(Comparator.comparing(Event::getDate))
                .map(Event::getCost)
                .orElseGet(() ->
                        switch (currency) {
                            case EUR -> settings.getStartCostEUR();
                            case USD -> settings.getStartCostUSD();
                            default -> NO_MULTIPLIER_VALUE;
                        }
                );
        return money * multiplier;
    }

    private double calculateNewDebt(double currentDebt, double rate, LocalDate creditDate,
                                    LocalDate transactionDate, int period, double transactionMoney) {
        long daysBetween = ChronoUnit.DAYS.between(creditDate, transactionDate);
        double accruedInterest = currentDebt * (1 + (rate / 100) * daysBetween / period);
        return accruedInterest - transactionMoney;
    }
}