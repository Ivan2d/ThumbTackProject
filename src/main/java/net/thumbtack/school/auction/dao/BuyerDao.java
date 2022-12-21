package net.thumbtack.school.auction.dao;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Price;
import net.thumbtack.school.auction.model.User;

import java.util.Collection;
import java.util.List;

public interface BuyerDao {
    void insert(User buyer) throws ServerException;
    User getByLogin(String login) throws ServerException;
    Lot getLot(int idLot) throws ServerException;
    Collection<Lot> getLotListByCategory(int idCategory);

    Collection<Lot> getLotListByCategoryList(List<Integer> idCategories);
    void addPrice(Price price) throws ServerException;

}
