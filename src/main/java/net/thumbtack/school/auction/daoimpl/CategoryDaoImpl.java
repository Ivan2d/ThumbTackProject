package net.thumbtack.school.auction.daoimpl;
import net.thumbtack.school.auction.dao.CategoryDao;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Category;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoryDaoImpl extends BaseDaoImpl implements CategoryDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoImpl.class);

    @Override
    public Category insert(Category category) {
        LOGGER.debug("DAO insert Seller {}", category);
        try (SqlSession sqlSession = getSession()) {
            try {
                getCategoryMapper(sqlSession).insert(category);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Book {} {}", category, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return category;
    }

    @Override
    public void addCategoryToLot(int idLot, int idCategory) throws ServerException {
        LOGGER.debug("DAO add Category to Lot {} {}", idLot, idCategory);
            try (SqlSession sqlSession = getSession()) {
                if (getLotMapper(sqlSession).find(idLot) == null){
                    throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
                }
                if (getCategoryMapper(sqlSession).find(idCategory) == null){
                    throw new ServerException(ServerErrorCode.CATEGORY_NOT_FOUND);
                }
                if (getCategoryMapper(sqlSession).findLot(idLot) != null &&
                        getCategoryMapper(sqlSession).findCategory(idCategory) != null){
                    throw new ServerException(ServerErrorCode.CATEGORY_ALREADY_HERE);
                }
                getCategoryMapper(sqlSession).addCategoryToLot(idLot, idCategory);
                sqlSession.commit();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get Book by Id {} {}", idLot, idCategory, ex);
                throw ex;
            }
    }

    @Override
    public void deleteCategoryFromLot(int idLot, int idCategory) throws ServerException {
        LOGGER.debug("DAO delete Category from Lot {} {}", idLot, idCategory);
        try (SqlSession sqlSession = getSession()) {
            if (getCategoryMapper(sqlSession).findLot(idLot) == null) {
                throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
            }
            if (getCategoryMapper(sqlSession).findCategory(idCategory) == null){
                throw new ServerException(ServerErrorCode.CATEGORY_NOT_FOUND);
            }
            getCategoryMapper(sqlSession).deleteCategoryFromLot(idCategory, idLot);
            sqlSession.commit();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't delete Category from Lot {} {} {}", idLot, idCategory, ex);
            throw ex;
        }
    }
}
