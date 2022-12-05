package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;

import java.util.List;

public interface BuyerDao {
    void insert(User buyer) throws ServerException;
    User get(String login) throws ServerException;
    Lot getLot(int idSeller, int idLot) throws ServerException;
    List<Lot> getLotListByCategory(int idCategory);
    void addPrice(int idSeller, int value, int idLot) throws ServerException;

    void deletePrice(int idPrice) throws ServerException;

}
