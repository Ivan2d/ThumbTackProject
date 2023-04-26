package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;

import java.util.Collection;

public interface LotDao {
    Lot insert(Lot lot) throws ServerException;
    Collection<Lot> getAllLazyBuyer(int idBuyer);
    Collection<Lot> getAllLazySeller(int idSeller);

    void deleteLot(int id) throws ServerException;
    Lot takeLot(int idLot) throws ServerException;
}
