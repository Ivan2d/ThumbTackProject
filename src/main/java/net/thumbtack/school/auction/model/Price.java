package net.thumbtack.school.auction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private int id;
    private int value;
    private Lot lot;
    private Buyer buyer;

    public Price(Buyer buyer, int value, Lot lot){
        setValue(value);
        setLot(lot);
        setBuyer(buyer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;

        Price price = (Price) o;

        if (getId() != price.getId()) return false;
        if (getValue() != price.getValue()) return false;
        if (getBuyer() != null ? !getBuyer().equals(price.getBuyer()) : price.getBuyer() != null) return false;
        return getLot() != null ? getLot().equals(price.getLot()) : price.getLot() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getBuyer() != null ? getBuyer().hashCode() : 0);
        result = 31 * result + getValue();
        result = 31 * result + (getLot() != null ? getLot().hashCode() : 0);
        return result;
    }
}
