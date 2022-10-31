package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.dto.request.LogoutSellerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.model.User;

import java.util.UUID;

public class SellerDaoImpl implements SellerDao
{
    @Override
    public void insert (User seller) throws UserException {
        DataBase.getInstance().insert(seller);
    }

    @Override
    public UUID loginUser(User user) throws UserException {
        return DataBase.getInstance().loginSeller(user);
    }

    @Override
    public void logoutUser(UUID uuid) throws UserException {
        DataBase.getInstance().logoutSeller(uuid);
    }

    @Override
    public User get(String login) throws UserException {
        return DataBase.getInstance().get(login);
    }
}
