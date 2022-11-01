package net.thumbtack.school.auction.model;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Setter
@Getter
public class Seller extends User {
    public Seller(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }
}
