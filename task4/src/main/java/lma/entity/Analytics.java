package lma.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
public class Analytics extends BaseEntity<Long> {

    private Long carId;

    private Long customerId;

    private LocalDate date;

    private Long saleAssistantId;

    private BigDecimal soldPrice;

    private Long discountId;
}
