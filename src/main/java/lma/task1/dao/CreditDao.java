package lma.task1.dao;

import lma.task1.loader.DataLoader;
import lma.task1.entity.Credit;

import java.util.List;

public class CreditDao {

    private static final CreditDao INSTANCE = new CreditDao();

    private CreditDao() {
    }

    public static CreditDao getInstance() {
        return INSTANCE;
    }

    public List<Credit> findAll() {
        return DataLoader.getFileData().getCredits();
    }
}
