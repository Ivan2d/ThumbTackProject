package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.dto.LoginBuyerDtoRequest;
import net.thumbtack.school.auction.dto.LogoutBuyerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.Seller;

import java.util.UUID;

public interface BuyerDao {
    UUID insert(Buyer buyer) throws UserException;
    UUID loginUser (LoginBuyerDtoRequest ludr) throws UserException;

    String logoutUser (LogoutBuyerDtoRequest ludr) throws UserException;


}
