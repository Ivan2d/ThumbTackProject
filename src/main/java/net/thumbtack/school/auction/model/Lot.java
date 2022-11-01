package net.thumbtack.school.auction.model;
import java.util.List;

// REVU и сюда lombok
public class Lot {
    private List<Category> categories;
    private String name;
    private String description;
    private int minValueForSell;
}
