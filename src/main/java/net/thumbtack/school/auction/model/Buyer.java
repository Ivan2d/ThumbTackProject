package net.thumbtack.school.auction.model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Buyer extends User {
    public Buyer(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }
}



