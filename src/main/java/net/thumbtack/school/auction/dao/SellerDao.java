package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dto.request.LoginSellerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutSellerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;

import java.util.UUID;
public interface SellerDao {
      UUID insert(Seller seller) throws UserException;
      UUID loginUser (LoginSellerDtoRequest loginSellerDtoRequest) throws UserException;
      ServerResponse logoutUser (LogoutSellerDtoRequest logoutSellerDtoRequest) throws UserException;
}

