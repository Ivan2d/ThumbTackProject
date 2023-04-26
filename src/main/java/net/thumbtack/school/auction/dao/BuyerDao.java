package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Seller;

public interface BuyerDao {
    Buyer insert(Buyer buyer) throws ServerException;
    void delete(int id) throws ServerException;
    Buyer getById(int id) throws ServerException;
    void addLotToBuyer(Buyer buyer, Lot lot);
    Lot takeLotByFromBuyer(Seller seller, int id) throws ServerException;
    void login(Buyer buyer) throws ServerException;

    void deleteFromSession(String token) throws ServerException;

    Buyer getByToken(String token) throws ServerException;
}
