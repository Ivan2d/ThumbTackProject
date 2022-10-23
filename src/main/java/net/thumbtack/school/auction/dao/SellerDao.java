package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dto.request.LoginSellerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutSellerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;

import java.util.UUID;
public interface SellerDao {
      UUID insert(Seller user) throws UserException;
      UUID loginUser (LoginSellerDtoRequest lsdr) throws UserException;
      ServerResponse logoutUser (LogoutSellerDtoRequest ludr) throws UserException;
}

