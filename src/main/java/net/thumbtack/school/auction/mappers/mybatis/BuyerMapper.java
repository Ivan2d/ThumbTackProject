package net.thumbtack.school.auction.mappers.mybatis;
import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Seller;
import org.apache.ibatis.annotations.*;

public interface BuyerMapper {
    @Insert("INSERT INTO buyer (userid) VALUES ( #{buyer.id} )")
    Integer insert(@Param("buyer") Buyer buyer);

    @Select("SELECT id, firstname, lastname, login, password FROM user WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
    })
    Buyer getById(int id);

    @Insert("INSERT INTO buyer_lot (buyerid, lotid) VALUES(#{buyer.id}, #{lot.id})")
    void addLotToBuyer(Buyer buyer, Lot lot);

    @Delete("DELETE FROM buyer")
    void deleteAll();

    @Delete("DELETE FROM buyer WHERE ( userid = #{id} )")
    void delete(int id);

    @Insert("INSERT INTO session (id, uuid) VALUES (#{buyer.id}, #{buyer.uuid})")
    void login(@Param("buyer") Buyer buyer);

}
