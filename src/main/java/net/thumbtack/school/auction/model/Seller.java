package net.thumbtack.school.auction.model;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Seller extends User {
    private List<Lot> lots = new ArrayList<>();
    public Seller(String firstName, String lastName, String login, String password, List<Lot> lots) {
        super(firstName, lastName, login, password);
        this.lots = lots;
    }
}
