package net.thumbtack.school.auction.model;
import lombok.*;

import java.util.List;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Lot {
    private List<Category> categories;
    private String name;
    private String description;
    private int minValueForSell;
    private Seller seller;
}
