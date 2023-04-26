package net.thumbtack.school.auction.model;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Buyer extends User {

    private List<Lot> lots = new ArrayList<>();
    public Buyer(String firstname, String lastname, String login, String password) {
        super(firstname, lastname, login, password);
        setLots(lots);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Buyer)) return false;
        if (!super.equals(o)) return false;

        Buyer buyer = (Buyer) o;

        return getLots() != null ? getLots().equals(buyer.getLots()) : buyer.getLots() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getLots() != null ? getLots().hashCode() : 0);
        return result;
    }
}



