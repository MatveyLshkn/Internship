package lma.task1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileData {

    private List<User> users;

    private List<Credit> credits;

    private List<Discount> discounts;

    private List<Event> events;

    private List<Transaction> transactions;
}