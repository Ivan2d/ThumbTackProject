package net.thumbtack.school.auction.model;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@AllArgsConstructor
@Getter
public class Seller extends User {
    private List<Lot> lots = new ArrayList<>();
    public Seller(String firstname, String lastname, String login, String password, List<Lot> lots) {
        super(firstname, lastname, login, password);
        this.lots = lots;
    }
    public void addLot(Lot lot){
        lots.add(lot);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        if (!super.equals(o)) return false;

        Seller seller = (Seller) o;

        return getLots() != null ? getLots().equals(seller.getLots()) : seller.getLots() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getLots() != null ? getLots().hashCode() : 0);
        return result;
    }
}
