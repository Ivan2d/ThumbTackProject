package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.dao.PriceDao;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Price;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceDaoImpl extends BaseDaoImpl implements PriceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceDaoImpl.class);
    @Override
    public Price insert(Price price) throws ServerException {
        LOGGER.debug("DAO add Price to Lot {}", price);
        try (SqlSession sqlSession = getSession()) {
            getPriceMapper(sqlSession).insert(price);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't add Price to Lot {} {}", price, ex);
            throw ex;
        }
        return price;
    }
}
