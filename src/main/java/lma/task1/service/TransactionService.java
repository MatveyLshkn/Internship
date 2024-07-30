package lma.task1.service;

import lma.task1.dao.TransactionDao;
import lma.task1.entity.Credit;
import lma.task1.entity.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransactionService {
    private static final TransactionService INSTANCE = new TransactionService();

    private final TransactionDao transactionDao = TransactionDao.getInstance();

    private TransactionService() {
    }

    public static TransactionService getInstance() {
        return INSTANCE;
    }

    private boolean isTransactionMatchesCreditAndDate(Transaction transaction, Long creditId,
                                                      LocalDate dateFrom, LocalDate dateTo) {
        return transaction != null &&
               (creditId != null && creditId.equals(transaction.getCreditId())) &&
               (dateFrom != null && transaction.getDate().isAfter(dateFrom)) &&
               (dateTo != null && transaction.getDate().isBefore(dateTo));
    }

    public List<Transaction> filterTransactionByCreditAndDate(Credit credit, LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom == null || dateFrom.isBefore(credit.getDate())) {
            dateFrom = credit.getDate();
        }
        LocalDate finalDateFrom = dateFrom;
        return transactionDao.findAll().stream()
                .filter(transaction -> isTransactionMatchesCreditAndDate(transaction, credit.getId(),
                        finalDateFrom, dateTo))
                .collect(Collectors.toList());
    }
}