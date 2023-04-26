package net.thumbtack.school.auction.mappers.mybatis;

import net.thumbtack.school.auction.model.Category;
import org.apache.ibatis.annotations.*;

public interface CategoryMapper {
    @Insert("INSERT INTO category (name) VALUES "
            + "( #{category.name} )")
    @Options(useGeneratedKeys = true, keyProperty = "category.id")
    Integer insert(@Param("category") Category category);

    @Insert("INSERT INTO category_lot (categoryid, lotid) VALUES(#{idCategory}, #{idLot})")
    void addCategoryToLot(@Param("idLot") int idLot, @Param("idCategory") int idCategory);

    @Delete("DELETE FROM category_lot WHERE ( lotid = #{idLot} AND categoryid = #{idCategory} )")
    void deleteCategoryFromLot(@Param("idCategory") int idCategory, @Param("idLot") int idLot);

    @Delete("DELETE FROM category")
    void deleteAll();

    @Select("SELECT id FROM category WHERE (id = #{id})")
    Integer find(int id);

    @Select("SELECT lotid FROM category_lot WHERE (lotid = #{idLot})")
    Integer findLot(int idLot);
    @Select("SELECT categoryid FROM category_lot WHERE (categoryid = #{idCategory})")
    Integer findCategory(int idCategory);
}
