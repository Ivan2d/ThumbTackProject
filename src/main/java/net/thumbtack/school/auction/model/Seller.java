package net.thumbtack.school.auction.model;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Setter
@Getter
public class Seller extends User {
    private List<Lot> lots;
    public Seller(String firstName, String lastName, String login, String password, List<Lot> lots) {
        super(firstName, lastName, login, password);
        this.lots = lots;
    }
}
