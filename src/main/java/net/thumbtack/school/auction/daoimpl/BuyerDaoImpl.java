package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.dto.request.LogoutBuyerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.User;

import java.util.UUID;

public class BuyerDaoImpl implements BuyerDao
{
    @Override
    public void insert(User user) throws UserException {
        DataBase.getInstance().insert(user);
    }

    @Override
    public UUID loginUser(User request) throws UserException {
        return DataBase.getInstance().loginBuyer(request);
    }

    @Override
    public void logoutUser(LogoutBuyerDtoRequest dtoRequest) throws UserException {
        DataBase.getInstance().logoutBuyer(dtoRequest.getToken());
    }

    @Override
    public User get(String login) throws UserException {
        return DataBase.getInstance().get(login);
    }

}
