package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.dto.response.UserDtoResponse;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.User;

import java.util.UUID;

public interface UserDao {

    UUID login(User user) throws ServerException;

    void logout(UUID token) throws ServerException;

    User get(String login) throws ServerException;

    User getUserByToken(UUID uuid) throws ServerException;
}
