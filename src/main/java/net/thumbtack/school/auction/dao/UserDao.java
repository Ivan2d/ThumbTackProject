package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.User;

import java.util.UUID;

public interface UserDao {

    UUID login(User user) throws UserException;

    void logout(UUID token) throws UserException;

    User get(String login) throws UserException;

    User getUserByToken(UUID uuid) throws UserException;
}
