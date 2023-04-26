package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Category;

public interface CategoryDao {
    Category insert(Category category);
    void addCategoryToLot(int idLot, int idCategory) throws ServerException;
    void deleteCategoryFromLot(int idLot, int idCategory) throws ServerException;
}
