package net.thumbtack.school.auction.daoimpl;
import lombok.Data;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;

public class SellerDaoImpl implements SellerDao
{
    @Override
    public void insert (User seller) throws UserException {
        DataBase.getInstance().insert(seller);
    }

    @Override
    public User get(String login) throws UserException {
        return DataBase.getInstance().get(login);
    }

    @Override
    public void addLot(Lot lot) throws UserException {
        DataBase.getInstance().addLot(lot);
    }
}
