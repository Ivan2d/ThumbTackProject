package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;
public interface SellerDao {
      void insert(User seller) throws UserException;
      User get(String login) throws UserException;
      void addLot(Lot lot) throws UserException;
}

