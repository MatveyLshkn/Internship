package lma.task1.dao;

import lma.task1.loader.DataLoader;
import lma.task1.entity.Transaction;

import java.util.List;

public class TransactionDao {
    private static final TransactionDao INSTANCE = new TransactionDao();

    private TransactionDao() {
    }

    public static TransactionDao getInstance() {
        return INSTANCE;
    }

    public List<Transaction> findAll() {
        return DataLoader.getFileData().getTransactions();
    }
}
