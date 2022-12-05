package net.thumbtack.school.auction.model;
import lombok.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Setter
@Getter

public class Buyer extends User {

    private List<Lot> lots;
    public Buyer(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
        setLots(lots);
    }
}



