package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.dao.SellerDao;

import java.util.UUID;

public class SellerDaoImpl implements SellerDao
{
    @Override
    public UUID insert (Seller user) throws UserException {
        return DataBase.getInstance().insert(user);
    }
}
