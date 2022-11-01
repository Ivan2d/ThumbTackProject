package net.thumbtack.school.auction.daoimpl;
import lombok.Data;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.User;
import java.util.UUID;

public class UserDaoImpl implements UserDao {
    @Override
    public UUID login(User user) throws UserException{
        return DataBase.getInstance().login(user);
    }

    @Override
    public void logout(UUID token) throws UserException{
        DataBase.getInstance().logout(token);
    }

    @Override
    public User get(String login) throws UserException{
        return DataBase.getInstance().get(login);
    }

    @Override
    public User getUserByToken(UUID uuid) throws UserException {
        return DataBase.getInstance().getByToken(uuid);
    }
}
