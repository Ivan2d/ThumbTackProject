package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.dto.request.LoginSellerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutSellerDtoRequest;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.dao.SellerDao;

import java.util.UUID;

public class SellerDaoImpl implements SellerDao
{
    @Override
    public UUID insert (Seller seller) throws UserException {
        return DataBase.getInstance().insert(seller);
    }

    @Override
    public UUID loginUser(LoginSellerDtoRequest loginSellerDtoRequest) throws UserException {
        return DataBase.getInstance().loginSeller(loginSellerDtoRequest);
    }

    @Override
    public ServerResponse logoutUser(LogoutSellerDtoRequest logoutSellerDtoRequest) throws UserException {
        return DataBase.getInstance().logoutSeller(logoutSellerDtoRequest);
    }
}
