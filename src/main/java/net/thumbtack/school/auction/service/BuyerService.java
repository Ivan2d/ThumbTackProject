package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.mapper.BuyerMapperFromBuyerDto;
import net.thumbtack.school.auction.mapper.BuyerMapperFromRegister;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Price;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.daoimpl.BuyerDaoImpl;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Buyer;
import java.util.Collection;
import java.util.UUID;

public class BuyerService {
    private BuyerDao buyerDao = new BuyerDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private static Gson gson = new Gson();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;


    public ServerResponse registerUser(String requestJsonString) {
        try {
            RegisterDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, RegisterDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
            Buyer buyer = BuyerMapperFromRegister.MAPPER.toBuyer(dtoRequest);
            buyerDao.insert(buyer);
            EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(emptySuccessDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getInfoLot(String token, String requestJsonString) {
        try {
            Buyer buyer = getBuyerByToken(token);
            GetLotInfoRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, GetLotInfoRequest.class);
            ServiceUtils.checkInfoSomeLotRequest(dtoRequest);
            Lot lot = buyerDao.getLot(dtoRequest.getIdLot());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(lot));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getLotsInfoByCategory(String token, String requestJsonString) {
        try {
            Buyer buyer = getBuyerByToken(token);
            GetLotsInfoByCategoryRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, GetLotsInfoByCategoryRequest.class);
            ServiceUtils.checkInfoAllLotsRequest(dtoRequest);
            Collection<Lot> list = buyerDao.getLotListByCategory(dtoRequest.getIdCategory());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(list));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse getLotsInfoByListCategory(String token, String requestJsonString) {
        try {
            Buyer buyer = getBuyerByToken(token);
            GetLotsInfoByListCategoryRequest dtoRequest = ServiceUtils.getObjectFromJson
                    (requestJsonString, GetLotsInfoByListCategoryRequest.class);
            ServiceUtils.checkInfoAllLotsListCategoryRequest(dtoRequest);
            Collection<Lot> list = buyerDao.getLotListByCategoryList(dtoRequest.getIdCategories());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(list));
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
            Lot lot = buyerDao.getLot(dtoRequest.getIdLot());
            buyerDao.addPrice(new Price(buyer, dtoRequest.getValue(), lot));
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }

    }

    public ServerResponse getBuyerByTokenResponse(String token) {
       try {
           Buyer buyer = getBuyerByToken(token);
           GetBuyerByTokenDtoResponse getBuyerByTokenDtoResponse =
                   BuyerMapperFromBuyerDto.MAPPER.fromBuyer(buyer);
           return new ServerResponse(CODE_SUCCESS, gson.toJson(getBuyerByTokenDtoResponse));
       }
       catch (ServerException e){
           return new ServerResponse(e);
       }
    }

    private Buyer getBuyerByToken(String token) throws ServerException {
        if (token == null){
            throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
        }
        User user = userDao.getUserByToken(UUID.fromString(token));
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        if (!(user instanceof Buyer)) {
            throw new ServerException(ServerErrorCode.NOT_A_BUYER);
        }
        return (Buyer) user;
    }

}
