package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.dao.LotDao;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Lot;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class LotDaoImpl extends BaseDaoImpl implements LotDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(LotDaoImpl.class);

    @Override
    public Lot insert(Lot lot) throws ServerException {
        LOGGER.debug("DAO insert Lot {}", lot);
        try (SqlSession sqlSession = getSession()) {
            try {
                getLotMapper(sqlSession).insert(lot);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Book {} {}", lot, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return lot;
    }

    @Override
    public Collection<Lot> getAllLazyBuyer(int idBuyer) {
        LOGGER.debug("DAO get all lazy ");
        try (SqlSession sqlSession = getSession()) {
            return getLotMapper(sqlSession).getAllLazyBuyer(idBuyer);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get All Lazy", ex);
            throw ex;
        }
    }

    @Override
    public Collection<Lot> getAllLazySeller(int idSeller) {
        LOGGER.debug("DAO get all lazy ");
        try (SqlSession sqlSession = getSession()) {
            return getLotMapper(sqlSession).getAllLazySeller(idSeller);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get All Lazy", ex);
            throw ex;
        }
    }

    @Override
    public void deleteLot(int id) throws ServerException {
        LOGGER.debug("DAO delete Lot by id ");
        try (SqlSession sqlSession = getSession()) {
            if (getLotMapper(sqlSession).find(id) == null){
                throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
            }
            getLotMapper(sqlSession).deleteLotById(id);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete Lot by id", ex);
            throw ex;
        }
    }
    @Override
    public Lot takeLot(int idLot) throws ServerException {
        LOGGER.debug("DAO get Lot by Seller {}", idLot);
        try (SqlSession sqlSession = getSession()) {
            if (getLotMapper(sqlSession).find(idLot) == null){
                throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
            }
            return getLotMapper(sqlSession).getById(idLot);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Book by Id {}", idLot, ex);
            throw ex;
        }
    }
}
