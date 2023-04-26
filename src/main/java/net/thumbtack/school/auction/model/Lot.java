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
    private String name;
    private String description;
    private int minValueForSell;
    private int obligatoryValueForSell;
    private Seller seller = new Seller();
    private List<Category> categories = new ArrayList<>();

    public Lot(List<Category> categories, String name, String description, int minValueForSell, Seller seller){
        setCategories(categories);
        setName(name);
        setDescription(description);
        setMinValueForSell(minValueForSell);
        setSeller(seller);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lot)) return false;

        Lot lot = (Lot) o;

        if (getId() != lot.getId()) return false;
        if (getMinValueForSell() != lot.getMinValueForSell()) return false;
        if (getObligatoryValueForSell() != lot.getObligatoryValueForSell()) return false;
        if (getCategories() != null ? !getCategories().equals(lot.getCategories()) : lot.getCategories() != null)
            return false;
        if (getName() != null ? !getName().equals(lot.getName()) : lot.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(lot.getDescription()) : lot.getDescription() != null)
            return false;
        return getSeller() != null ? getSeller().equals(lot.getSeller()) : lot.getSeller() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getCategories() != null ? getCategories().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getMinValueForSell();
        result = 31 * result + getObligatoryValueForSell();
        result = 31 * result + (getSeller() != null ? getSeller().hashCode() : 0);
        return result;
    }
}
