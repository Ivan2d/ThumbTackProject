package net.thumbtack.school.auction.model;
import lombok.Data;
import lombok.EqualsAndHashCode;
@EqualsAndHashCode(callSuper = true)
@Data
public class Seller extends User {
    public Seller(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }
}
