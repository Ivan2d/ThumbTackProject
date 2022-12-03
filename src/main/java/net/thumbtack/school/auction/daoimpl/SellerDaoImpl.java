package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;

public class SellerDaoImpl implements SellerDao
{
    @Override
    public void insert (User seller) throws ServerException {
        DataBase.getInstance().insert(seller);
    }

    @Override
    public User get(String login) throws ServerException {
        return DataBase.getInstance().get(login);
    }

    @Override
    public void addLot(Lot lot) throws ServerException {
        DataBase.getInstance().addLot(lot);
    }

    @Override
    public void deleteLot(int ID) throws ServerException {
        DataBase.getInstance().deleteLot(ID);
    }
}
