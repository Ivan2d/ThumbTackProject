package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.User;

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

}
