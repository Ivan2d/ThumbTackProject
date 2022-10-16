package net.thumbtack.school.auction.dao;

import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;

import java.util.UUID;
public interface SellerDao {
      UUID insert(Seller user) throws UserException;
}

