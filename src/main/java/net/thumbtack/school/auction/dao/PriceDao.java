package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Price;

public interface PriceDao {
    Price insert(Price price) throws ServerException;
}
