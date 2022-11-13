package net.thumbtack.school.auction.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.auction.model.Seller;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddLotDtoRequest {
    private List<Integer> categories;
    private String name;
    private String description;
    private int minValueForSell;
    private int seller;
}
