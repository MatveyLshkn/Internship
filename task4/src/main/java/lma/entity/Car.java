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
public class Car extends BaseEntity<Long>{

    private Long modelId;

    private String color;

    private LocalDate productionDate;

    private BigDecimal price;

    private Long mileage;
}
