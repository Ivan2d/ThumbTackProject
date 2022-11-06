package net.thumbtack.school.auction.model;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Setter
@Getter

public class Buyer extends User {
    public Buyer(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }
}



