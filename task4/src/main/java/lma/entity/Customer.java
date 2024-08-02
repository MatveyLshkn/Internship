package lma.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Customer extends BaseEntity<Long>{

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;
}
