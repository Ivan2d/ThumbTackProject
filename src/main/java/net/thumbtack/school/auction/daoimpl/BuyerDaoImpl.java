package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.dto.LoginBuyerDtoRequest;
import net.thumbtack.school.auction.dto.LogoutBuyerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Buyer;

import java.util.UUID;

public class BuyerDaoImpl implements BuyerDao
{
    @Override
    public UUID insert(Buyer user) throws UserException {
        return DataBase.getInstance().insert(user);
    }

    @Override
    public UUID loginUser(LoginBuyerDtoRequest ludr) throws UserException {
        return DataBase.getInstance().loginBuyer(ludr);
    }

    @Override
    public String logoutUser(LogoutBuyerDtoRequest ludr) throws UserException {
        return null;
    }
}
