package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.dto.LoginSellerDtoRequest;
import net.thumbtack.school.auction.dto.LogoutSellerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;

import java.util.UUID;
public interface SellerDao {
      UUID insert(Seller user) throws UserException;
      UUID loginUser (LoginSellerDtoRequest lsdr) throws UserException;
      String logoutUser (LogoutSellerDtoRequest ludr) throws UserException;
}

