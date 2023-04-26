package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Seller;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SellerDaoImpl extends BaseDaoImpl implements SellerDao
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerDaoImpl.class);

    @Override
    public Seller insert(Seller seller) {
        LOGGER.debug("DAO insert Seller {}", seller);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(seller);
                getSellerMapper(sqlSession).insert(seller);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Book {} {}", seller, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return seller;
    }

    @Override
    public void delete(int id) throws ServerException {
        LOGGER.debug("DAO delete Seller {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSellerMapper(sqlSession).delete(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Book {}", id, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public Seller getById(int id) {
        LOGGER.debug("DAO get Seller by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getSellerMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {} {}", id, ex);
            throw ex;
        }
    }
    @Override
    public void addLotToSeller(Seller seller, Lot lot) throws ServerException {
        LOGGER.debug("DAO add Lot to Seller {} {}", seller, lot);
        try (SqlSession sqlSession = getSession()) {
            if (getSellerMapper(sqlSession).find(seller) == null){
                throw new ServerException(ServerErrorCode.NOT_A_SELLER);
            }
            lot.setSeller(seller);
            getLotMapper(sqlSession).insert(lot);
            getSellerMapper(sqlSession).addLotToSeller(seller, lot);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't add Lot to Seller {} {}", seller, lot, ex);
            throw ex;
        }
    }

    @Override
    public void login(Seller seller) throws ServerException {
        LOGGER.debug("DAO get Lot by Buyer {}", seller);
        try (SqlSession sqlSession = getSession()) {
            Integer id = getUserMapper(sqlSession).find(seller);
            if(id == null){
                throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            seller.setId(id);
            getSellerMapper(sqlSession).login(seller);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {}", seller, ex);
            throw ex;
        }
    }

    @Override
    public Seller getByToken(String token) throws ServerException {
        LOGGER.debug("DAO get User {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                Integer id = getSessionMapper(sqlSession).takeIdByUUID(token);
                if(id == null){
                    throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
                }
                return getSellerMapper(sqlSession).getById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete User {} {}", token, ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

    @Override
    public void deleteFromSession(String token) throws ServerException {
        LOGGER.debug("DAO get Lot by Buyer {}", token);
        try (SqlSession sqlSession = getSession()) {
            if(token == null){
                throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
            }
            getSessionMapper(sqlSession).deleteByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {}", token, ex);
            throw ex;
        }
    }
}
