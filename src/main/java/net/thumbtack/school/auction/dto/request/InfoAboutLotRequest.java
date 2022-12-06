package net.thumbtack.school.auction.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// REVU GetLotInfoRequest
public class InfoAboutLotRequest {
    private int idLot;
    // REVU лишнее. Хватит и idLot
    // по idLot можно всегда найти его Seller
    private int idSeller;
}
