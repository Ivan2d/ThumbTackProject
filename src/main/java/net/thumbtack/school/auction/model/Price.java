package net.thumbtack.school.auction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// REVU скорее Bid
public class Price {
    private int id = -1;
    private Buyer buyer;
    private int value;
    private Lot lot;

    public Price(Buyer buyer, int value, Lot lot){
        setValue(value);
        setLot(lot);
        setBuyer(buyer);
        // REVU это я вообще не понимаю
        // получится в итоге 0, но каким-то очень хитрым способом. Зачем ?
        // это когда в БД сохраняете, там надо счетчик продвингать, а тут какой смысл ?
        // это же локальное поле
        id++;
    }
}
