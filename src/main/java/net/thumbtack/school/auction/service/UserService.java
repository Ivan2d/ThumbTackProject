package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.BuyerDaoImpl;
import net.thumbtack.school.auction.daoimpl.SellerDaoImpl;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.dto.request.RegisterBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterSellerDtoRequest;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.dto.response.GetUserByTokenDtoResponse;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.mappers.mapstruct.BuyerMapperFromRegister;
import net.thumbtack.school.auction.mappers.mapstruct.SellerMapperFromRegister;
import net.thumbtack.school.auction.mappers.mapstruct.UserMapperFromLogin;
import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;

public class UserService {
    private UserDao userDao = new UserDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private BuyerDao buyerDao = new BuyerDaoImpl();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static final Gson gson = new Gson();

    public ServerResponse registerBuyer(String requestJsonString) {
        try {
            RegisterBuyerDtoRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, RegisterBuyerDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
            Buyer buyer = BuyerMapperFromRegister.MAPPER.toBuyer(dtoRequest);
            buyerDao.insert(buyer);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse registerSeller(String requestJsonString) {
        try {
            RegisterSellerDtoRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, RegisterSellerDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
            Seller seller = SellerMapperFromRegister.MAPPER.toSeller(dtoRequest);
            sellerDao.insert(seller);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getUserByTokenResponse(String token) {
        try {
           User user = getUserByToken(token);
           GetUserByTokenDtoResponse getUserByTokenDtoResponse = UserMapperFromLogin.MAPPER.fromUser(user);
           return new ServerResponse(CODE_SUCCESS, gson.toJson(getUserByTokenDtoResponse));
       }
       catch (ServerException e){
           return new ServerResponse(e);
       }
    }

    private User getUserByToken(String token) throws ServerException {
        User user = userDao.getByToken(token);
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        return user;
    }
}
