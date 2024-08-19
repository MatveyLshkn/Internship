package lma.entity;

import lma.enums.CarTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Model extends BaseEntity<Long>{

    private CarTypeEnum carType;

    private String brand;

    private String modelName;
}
