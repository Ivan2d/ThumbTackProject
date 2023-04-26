package net.thumbtack.school.auction.mappers.mybatis;
import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Insert("INSERT INTO user (firstname, lastname, login, password) VALUES "
            + "( #{user.firstname}, #{user.lastname}, #{user.login}, #{user.password} )")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    Integer insert(@Param("user") User user);

    @Delete("DELETE FROM user")
    void deleteAll();
    @Delete("DELETE FROM user WHERE (id = #{id}")
    void delete(int id);

    @Select("SELECT id FROM user WHERE (login = #{user.login})")
    Integer find(@Param("user") User user);

    @Select("SELECT id, firstname, lastname, login, password FROM user WHERE (id = #{id})")
    User getById(Integer id);
}
