package net.thumbtack.school.auction.daoimpl;
import lombok.Data;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;

import java.util.List;

public class BuyerDaoImpl implements BuyerDao
{
    @Override
    public void insert(User user) throws UserException {
        DataBase.getInstance().insert(user);
    }
    @Override
    public User get(String login) throws UserException {
        return DataBase.getInstance().get(login);
    }

    @Override
    public Lot getLot(int idSeller, int idLot) throws UserException {
       return DataBase.getInstance().getLotBySeller(idSeller, idLot);
    }

    @Override
    public List<Lot> getLotListByCategory(int idCategory) {
        return DataBase.getInstance().getListByCategory(idCategory);
    }

}
