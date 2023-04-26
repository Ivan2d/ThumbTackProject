package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.dao.CommonDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDaoImpl extends BaseDaoImpl implements CommonDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);
    @Override
    public void clear() {
        LOGGER.debug("Clear Database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteAll();
                getCategoryMapper(sqlSession).deleteAll();
                getLotMapper(sqlSession).deleteAll();
                getPriceMapper(sqlSession).deleteAll();
                getSessionMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't clear database");
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
