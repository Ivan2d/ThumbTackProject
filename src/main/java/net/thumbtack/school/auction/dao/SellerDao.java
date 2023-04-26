package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Seller;
public interface SellerDao {
      Seller insert(Seller seller) throws ServerException;
      void delete(int id) throws ServerException;
      Seller getById(int id) throws ServerException;
      void addLotToSeller(Seller seller, Lot lot) throws ServerException;
      void login(Seller seller) throws ServerException;

      Seller getByToken(String token) throws ServerException;

      void deleteFromSession(String token) throws ServerException;
}

