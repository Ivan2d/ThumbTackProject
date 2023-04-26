package net.thumbtack.school.auction.mappers.mybatis;
import net.thumbtack.school.auction.model.Lot;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.Collection;
import java.util.List;

public interface LotMapper {

    @Insert("INSERT INTO lot ( name, idSeller, description, minValueForSell, obligatoryValueForSell) VALUES "
            + "( #{lot.name}, #{lot.seller.id}, #{lot.description}, #{lot.minValueForSell}, #{lot.obligatoryValueForSell} )")
    @Options(useGeneratedKeys = true, keyProperty = "lot.id")
    Integer insert(@Param("lot") Lot lot);


    @Select("SELECT * from buyer_lot WHERE (buyerid = #{buyer.id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "lots", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.auction.mappers.mybatis.BuyerMapper.getLotsByBuyer",
                            fetchType = FetchType.EAGER)),
    })
    Collection<Lot> getAllLazyBuyer(int id);


    @Select("SELECT * from seller_lot WHERE (sellerid = #{seller.id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "lots", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.auction.mappers.mybatis.SellerMapper.getLotsBySeller",
                            fetchType = FetchType.EAGER)),
    })
    Collection<Lot> getAllLazySeller(int idSeller);

    @Delete("DELETE FROM lot WHERE (id = #{id})")
    void deleteLotById(int id);

    @Select("SELECT name, description, minValueForSell, obligatoryValueForSell FROM lot " +
            "WHERE id = #{idLot}")
    Lot getById(int idLot);

    @Delete("DELETE FROM lot")
    void deleteAll();

    @Select("SELECT id FROM lot WHERE id = #{id}")
    Integer find(int id);
}