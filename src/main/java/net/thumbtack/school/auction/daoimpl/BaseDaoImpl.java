package net.thumbtack.school.auction.daoimpl;

import net.thumbtack.school.auction.mappers.mybatis.*;
import net.thumbtack.school.auction.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;


public class BaseDaoImpl {

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected BuyerMapper getBuyerMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(BuyerMapper.class);
    }

    protected SellerMapper getSellerMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SellerMapper.class);
    }

    protected CategoryMapper getCategoryMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(CategoryMapper.class);
    }
    protected LotMapper getLotMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(LotMapper.class);
    }
    protected PriceMapper getPriceMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(PriceMapper.class);
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }
    protected SessionMapper getSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SessionMapper.class);
    }

}