package net.thumbtack.school.auction.mappers.mybatis;

import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface SellerMapper {
    @Insert("INSERT INTO seller (userid) VALUES ( #{seller.id})")
    Integer insert(@Param("seller") User user);

    @Select("SELECT id, firstname, lastname, login, password FROM user WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
    })
    Seller getById(int id);

    @Insert("INSERT INTO seller_lot (sellerid, lotid) VALUES(#{seller.id}, #{lot.id})")
    void addLotToSeller(@Param("seller") Seller seller, @Param("lot") Lot lot);

    @Select("SELECT lotid FROM seller_lot WHERE (sellerid = #{seller.id} AND lotid = #{id})")
    Integer takeLotByFromSeller(@Param("seller") Seller seller, @Param("id") int id);

    @Delete("DELETE FROM seller")
    void deleteAll();

    @Delete("DELETE FROM seller WHERE ( userid = #{id} )")
    void delete(int id);

    @Insert("INSERT INTO session (id, uuid) VALUES (#{seller.id}, #{seller.uuid})")
    void login(@Param("seller") Seller seller);

    @Select("SELECT userid FROM seller WHERE (userid = #{seller.id})")
    Integer find(@Param("seller") Seller seller);
}
