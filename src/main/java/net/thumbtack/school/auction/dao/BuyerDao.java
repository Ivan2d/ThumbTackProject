package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.User;
public interface BuyerDao {
    void insert(User buyer) throws UserException;
    User get(String login) throws UserException;

}
