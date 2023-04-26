package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.User;

public interface UserDao {
    User register(User user) throws ServerException;
    void delete(int id) throws ServerException;
    void deleteAll() throws ServerException;
    User getByToken(String token) throws ServerException;
}
