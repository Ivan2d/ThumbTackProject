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
public class AddLotDtoRequest {
    private List<Integer> categories;
    private String name;
    private String description;
    private int minValueForSell;
    // REVU это его id ? Тогдв selleerId
    // сейчас непонятно
    private int seller;
}
