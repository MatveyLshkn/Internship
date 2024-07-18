package lma.task1.service;

import lma.task1.dao.CreditDao;
import lma.task1.entity.Credit;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CreditService {
    private static final CreditService INSTANCE = new CreditService();
    private final CreditDao creditDao = CreditDao.getInstance();

    private CreditService() {
    }

    public static CreditService getInstance() {
        return INSTANCE;
    }

    private boolean isCreditFitsTime(Credit credit, LocalDate startDate, LocalDate endDate) {
        return credit != null
               && startDate == null ? true : credit.getDate().isAfter(startDate)
               && endDate == null ? true : credit.getDate().isBefore(endDate);
    }

    public List<Credit> getCreditsByDate(LocalDate startDate, LocalDate endDate) {
        return creditDao.findAll().stream()
                .filter(credit -> isCreditFitsTime(credit, startDate, endDate))
                .collect(Collectors.toList());
    }
}