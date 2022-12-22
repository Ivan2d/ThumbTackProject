package net.thumbtack.school.auction.model;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Lot {
    private int id = 1;
    private List<Category> categories = new ArrayList<>();
    private String name;
    private String description;
    private int minValueForSell;
    private int obligatoryValueForSell;
    private Seller seller = new Seller();

    public Lot(List<Category> categories, String name, String description, int minValueForSell, Seller seller){
        setCategories(categories);
        setName(name);
        setDescription(description);
        setMinValueForSell(minValueForSell);
        setSeller(seller);
    }
}
