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
    private int Bid;
    private Buyer buyer;
    private int value;
    private Lot lot;

    public Price(Buyer buyer, int value, Lot lot){
        setValue(value);
        setLot(lot);
        setBuyer(buyer);
    }
}
