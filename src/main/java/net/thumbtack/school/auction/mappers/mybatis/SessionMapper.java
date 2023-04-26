package net.thumbtack.school.auction.mappers.mybatis;

import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SessionMapper {
    @Insert("INSERT INTO session (id, uuid) VALUES" +
            "( #{user.id}, #{user.uuid} )")
    Integer insert(@Param("user") User user);

    @Select("SELECT id FROM session WHERE (uuid = #{token})")
    Integer takeIdByUUID(String token);

    @Delete("DELETE FROM session WHERE (uuid = #{token})")
    void deleteByToken(String token);

    @Delete("DELETE FROM seller")
    void deleteAll();
}
