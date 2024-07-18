package lma.task1.entity;

import lma.task1.enums.ShowTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowFor {
    ShowTypeEnum type;
    List<Object> users;
}