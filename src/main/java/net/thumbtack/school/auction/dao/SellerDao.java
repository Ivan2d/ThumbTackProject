package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.model.User;

import java.util.UUID;
public interface SellerDao {
      void insert(User seller) throws UserException;
      UUID loginUser (User seller) throws UserException;
      void logoutUser (UUID uuid) throws UserException;

      User get(String login) throws UserException;
}

