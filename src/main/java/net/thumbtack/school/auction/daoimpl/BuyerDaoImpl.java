package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Seller;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BuyerDaoImpl extends BaseDaoImpl implements BuyerDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerDaoImpl.class);

    @Override
    public Buyer insert(Buyer buyer) {
        LOGGER.debug("DAO insert Buyer {}", buyer);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(buyer);
                getBuyerMapper(sqlSession).insert(buyer);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Book {} {}", buyer, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return buyer;
    }

    @Override
    public void delete(int id) throws ServerException {
        LOGGER.debug("DAO delete Buyer by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getBuyerMapper(sqlSession).delete(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Book {}", id, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public Buyer getById(int id) {
        LOGGER.debug("DAO get Buyer by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getBuyerMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public void addLotToBuyer(Buyer buyer, Lot lot) {
        LOGGER.debug("DAO add Lot to Buyer {} {}", buyer, lot);
        try (SqlSession sqlSession = getSession()) {
            getBuyerMapper(sqlSession).addLotToBuyer(buyer, lot);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {} {}", buyer, lot, ex);
            throw ex;
        }
    }

    @Override
    public Lot takeLotByFromBuyer(Seller seller, int id) throws ServerException {
        LOGGER.debug("DAO get Lot by Buyer {} {}", seller, id);
        try (SqlSession sqlSession = getSession()) {
          if (getSellerMapper(sqlSession).find(seller) == null){
              throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
          }
          if (getLotMapper(sqlSession).getById(id) == null) {
              throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
          }
          Integer idLot = getSellerMapper(sqlSession).takeLotByFromSeller(seller, id);
          if (idLot == null) {
              throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
          }
          return getLotMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {} {}", seller, id, ex);
            throw ex;
        }
    }

    @Override
    public void login(Buyer buyer) throws ServerException {
        LOGGER.debug("DAO get Lot by Buyer {}", buyer);
        try (SqlSession sqlSession = getSession()) {
            Integer id = getUserMapper(sqlSession).find(buyer);
            if(id == null){
                throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            buyer.setId(id);
            getBuyerMapper(sqlSession).login(buyer);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {}", buyer, ex);
            throw ex;
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

    @Override
    public Buyer getByToken(String token) throws ServerException {
        LOGGER.debug("DAO get User {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                Integer id = getSessionMapper(sqlSession).takeIdByUUID(token);
                if(id == null){
                    throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
                }
                return getBuyerMapper(sqlSession).getById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete User {}", token, ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }

}
