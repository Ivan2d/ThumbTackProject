package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;

import java.util.List;

public class BuyerDaoImpl implements BuyerDao
{
    @Override
    public void insert(User user) throws ServerException {
        DataBase.getInstance().insert(user);
    }
    @Override
    public User get(String login) {
        return DataBase.getInstance().get(login);
    }

    @Override
    public Lot getLot(int idSeller, int idLot) throws ServerException {
       return DataBase.getInstance().getLotBySeller(idSeller, idLot);
    }

    @Override
    public List<Lot> getLotListByCategory(int idCategory) {
        return DataBase.getInstance().getListByCategory(idCategory);
    }

    @Override
    public void addPrice(int idBuyer, int value, int idLot) {
        DataBase.getInstance().addPrice(idBuyer, value, idLot);
    }

    @Override
    public void deletePrice(int idPrice) {
        DataBase.getInstance().deletePrice(idPrice);
    }

}
