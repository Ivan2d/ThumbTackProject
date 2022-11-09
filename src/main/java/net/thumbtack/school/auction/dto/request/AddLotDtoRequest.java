package net.thumbtack.school.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.thumbtack.school.auction.model.Category;
import net.thumbtack.school.auction.model.Seller;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddLotDtoRequest {
    // REVU Category - класс модели, его использовать в DTO нельзя
    // проверьте, кстати и другие DTO
    // а надо сделать int id в категории
    // и передавать этот id
    // давайте об этих id побеседуем голосом.
    private List<Category> categories;
    private String name;
    private String description;
    private int minValueForSell;
    private Seller seller;
}
