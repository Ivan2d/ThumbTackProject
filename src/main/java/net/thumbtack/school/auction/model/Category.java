package net.thumbtack.school.auction.model;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter


public class Category {
    private int id;
    private String name;
    public Category(Integer id, String name){
        setId(id);
        setName(name);
    }

    public Category(String name) {
        setId(0);
        setName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (getId() != category.getId()) return false;
        return getName() != null ? getName().equals(category.getName()) : category.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
