package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.dto.response.UserDtoResponse;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.User;
import java.util.UUID;

public class UserDaoImpl implements UserDao {
    @Override
    public UUID login(User user) throws ServerException {
        return DataBase.getInstance().login(user);
    }

    @Override
    public void logout(UUID token) throws ServerException {
        DataBase.getInstance().logout(token);
    }

    @Override
    public User get(String login) throws ServerException {
        return DataBase.getInstance().get(login);
    }

    @Override
    public UserDtoResponse getUserByToken(UUID uuid) throws ServerException {
        return DataBase.getInstance().getByToken(uuid);
    }
}
