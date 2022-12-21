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
}
