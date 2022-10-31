package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.dto.request.LogoutBuyerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.User;

import java.util.UUID;

public interface BuyerDao {
    void insert(User buyer) throws UserException;
    UUID loginUser (User user) throws UserException;
    void logoutUser (LogoutBuyerDtoRequest logoutBuyerDtoRequest) throws UserException;

    User get(String login) throws UserException;


}
