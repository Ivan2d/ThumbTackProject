package net.thumbtack.school.auction.model;
import lombok.*;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@Setter
@Getter


public class Category {
    private int id;
    private String name;
    public Category(String name){
        setName(name);
    }
}
