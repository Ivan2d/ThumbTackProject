package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.*;
import net.thumbtack.school.auction.daoimpl.*;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.mappers.mapstruct.BuyerMapperFromLogin;
import net.thumbtack.school.auction.model.*;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.ServerException;

import java.util.UUID;

public class BuyerService {
    private SellerDao sellerDao = new SellerDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private BuyerDao buyerDao = new BuyerDaoImpl();
    private LotDao lotDao = new LotDaoImpl();
    private PriceDao priceDao = new PriceDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private static Gson gson = new Gson();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;

    public ServerResponse loginBuyer(String requestJsonString) {
        try {
            LoginDtoRequest loginDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
            ServiceUtils.checkRequest(loginDtoRequest);
            Buyer buyer = BuyerMapperFromLogin.MAPPER.toBuyer(loginDtoRequest);
            buyer.setUuid(UUID.randomUUID().toString());
            buyerDao.login(buyer);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new TokenDtoResponse(buyer.getUuid())));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

    public ServerResponse logoutBuyer(String token)
    {
        try {
            buyerDao.deleteFromSession(token);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        }
        catch (ServerException e){
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

    public ServerResponse getInfoLot(String token, String requestJsonString) {
        try {
            Seller seller = sellerDao.getByToken(token);
            GetLotInfoRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, GetLotInfoRequest.class);
            ServiceUtils.checkInfoSomeLotRequest(dtoRequest);
            Lot lot = buyerDao.takeLotByFromBuyer(seller, dtoRequest.getIdLot());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(lot));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse addPrice(String token, String requestJsonString) {
        try {
            Buyer buyer = getBuyerByToken(token);
            AddPriceDtoRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, AddPriceDtoRequest.class);
            ServiceUtils.checkAddPrice(dtoRequest);
            Lot lot = lotDao.takeLot(dtoRequest.getIdLot());
            priceDao.insert(new Price(buyer, dtoRequest.getValue(), lot));
            return new ServerResponse
                    (CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }

    }

    private Buyer getBuyerByToken(String token) throws ServerException {
        Buyer buyer = buyerDao.getByToken(token);
        if (buyer == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        return buyer;
    }

}
