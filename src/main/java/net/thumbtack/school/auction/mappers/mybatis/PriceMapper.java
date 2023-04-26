package net.thumbtack.school.auction.mappers.mybatis;
import net.thumbtack.school.auction.model.Category;
import net.thumbtack.school.auction.model.Price;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface PriceMapper {
    @Insert("INSERT INTO price (value) VALUES "
            + "( #{price.value} )")
    @Options(useGeneratedKeys = true, keyProperty = "price.id")
    Integer insert(@Param("price") Price price);

    @Delete("DELETE FROM price")
    void deleteAll();
}
