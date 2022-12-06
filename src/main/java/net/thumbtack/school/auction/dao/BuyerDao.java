package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Price;
import net.thumbtack.school.auction.model.User;

import java.util.Collection;
public interface BuyerDao {
    void insert(User buyer) throws ServerException;
    User getByLogin(String login) throws ServerException;
    Lot getLot(int idLot) throws ServerException;
    Collection<Lot> getLotListByCategory(int idCategory);
    void addPrice(Price price) throws ServerException;

}
