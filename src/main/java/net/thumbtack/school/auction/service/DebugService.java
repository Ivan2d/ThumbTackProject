package net.thumbtack.school.auction.service;

import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;

public class DebugService {
    private final UserDao userDao = new UserDaoImpl();
    public void clear() { userDao.clear(); }
}
