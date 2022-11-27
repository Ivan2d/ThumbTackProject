package net.thumbtack.school.auction.dao;
import lombok.Data;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;

import java.util.List;

public interface BuyerDao {
    void insert(User buyer) throws UserException;
    User get(String login) throws UserException;
    Lot getLot(int idSeller, int idLot) throws  UserException;
    List<Lot> getLotListByCategory(int idCategory);
    void addPrice(int idSeller, int value, int idLot);

    void deletePrice(int idPrice);

}
