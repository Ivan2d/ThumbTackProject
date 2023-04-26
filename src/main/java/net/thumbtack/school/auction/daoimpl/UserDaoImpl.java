package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User register(User user) throws ServerException {
        LOGGER.debug("DAO insert User {}", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert User {} {}", user, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return user;
    }

    @Override
    public void delete(int id) throws ServerException {
        LOGGER.debug("DAO delete User {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).delete(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete User {} {}", id, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() throws ServerException {
        LOGGER.debug("DAO delete all");
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteAll();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete all", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }

    @Override
    public User getByToken(String token) throws ServerException {
        LOGGER.debug("DAO get User {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                Integer id = getSessionMapper(sqlSession).takeIdByUUID(token);
                if(id == null){
                    throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
                }
                return getUserMapper(sqlSession).getById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete User {} {}", token, ex);
                sqlSession.rollback();
                throw ex;
            }
        }
    }
}
