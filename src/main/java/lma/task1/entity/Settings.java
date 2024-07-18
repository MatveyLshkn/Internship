package lma.task1.entity;

import lma.task1.enums.SortFieldEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Settings {

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private ShowFor showFor;

    private SortFieldEnum sortBy;

    private String[] useDepartments;

    private Double startCostEUR;

    private Double startCostUSD;

}