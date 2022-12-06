package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;
public interface SellerDao {
      void insert(User seller) throws ServerException;
      User getByLogin(String login) throws ServerException;
      void addLot(Lot lot) throws ServerException;

      void deleteLot(int id) throws ServerException;
}

