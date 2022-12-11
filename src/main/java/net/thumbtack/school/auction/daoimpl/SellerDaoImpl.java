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
    public User getByLogin(String login) {
        return DataBase.getInstance().getByLogin(login);
    }

    @Override
    public void addLot(Lot lot) {
        DataBase.getInstance().addLot(lot);
    }

    @Override
    public void deleteLot(int id) throws ServerException {
        DataBase.getInstance().deleteLot(id);
    }

    @Override
    public void addCategoryToLot(int idLot, int idCategory) throws ServerException {
        DataBase.getInstance().addCategoryToLot(idLot, idCategory);
    }

    @Override
    public void deleteCategoryFromLot(int idLot, int idCategory) throws ServerException {
        DataBase.getInstance().deleteCategoryFromLot(idLot, idCategory);
    }
}
