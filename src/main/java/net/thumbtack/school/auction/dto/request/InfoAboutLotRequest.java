package net.thumbtack.school.auction.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoAboutLotRequest {
    // REVU гоните Of везде
    // lotId
    // sellerId
    private int idOfLot;
    private int idOfSeller;
}
