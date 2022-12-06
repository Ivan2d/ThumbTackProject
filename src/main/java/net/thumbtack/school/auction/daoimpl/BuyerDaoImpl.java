package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Price;
import net.thumbtack.school.auction.model.User;
import java.util.Collection;

public class BuyerDaoImpl implements BuyerDao
{
    @Override
    public void insert(User user) throws ServerException {
        DataBase.getInstance().insert(user);
    }
    @Override
    public User getByLogin(String login) {
        return DataBase.getInstance().getByLogin(login);
    }

    @Override
    public Lot getLot(int idLot) throws ServerException {
       return DataBase.getInstance().getLotBySeller(idLot);
    }

    @Override
    public Collection<Lot> getLotListByCategory(int idCategory) {
        return DataBase.getInstance().getListByCategory(idCategory);
    }

    @Override
    public void addPrice(Price price) {
        DataBase.getInstance().addPrice(price);
    }

}
