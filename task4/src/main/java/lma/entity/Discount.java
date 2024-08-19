package lma.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
public class Discount extends BaseEntity<Long>{

    private Double percentage;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long modelId;
}
