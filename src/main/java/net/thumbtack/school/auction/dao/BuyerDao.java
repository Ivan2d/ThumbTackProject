package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.dto.request.LoginBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutBuyerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Buyer;

import java.util.UUID;

public interface BuyerDao {
    UUID insert(Buyer buyer) throws UserException;
    UUID loginUser (LoginBuyerDtoRequest ludr) throws UserException;

    String logoutUser (LogoutBuyerDtoRequest ludr) throws UserException;


}
