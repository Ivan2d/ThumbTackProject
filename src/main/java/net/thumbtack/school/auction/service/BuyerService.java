package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.mapper.BuyerMapperFromRegister;
import net.thumbtack.school.auction.mapper.UserMapperFromLogin;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.daoimpl.BuyerDaoImpl;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Buyer;
import java.util.List;
import java.util.UUID;

public class BuyerService {
    private static BuyerDao buyerDao = new BuyerDaoImpl();
    private static UserDao userDao = new UserDaoImpl();
    private static Gson gson = new Gson();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;


    public ServerResponse registerUser(String requestJsonString) throws JsonSyntaxException {
        try {
            RegisterDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, RegisterDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
            Buyer buyer = BuyerMapperFromRegister.MAPPER.toBuyer(dtoRequest);
            buyerDao.insert(buyer);
            EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(emptySuccessDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, gson.toJson(new ErrorDtoResponse(e)));
        }
    }

    // REVU где передача токена ?
    public ServerResponse takeInfoAboutSomeLot(String token, String requestJsonString) throws JsonSyntaxException, ServerException {
        // REVU и тут try и тут валидация, и тут catch
        try {
            Buyer buyer = getSellerByToken(token);
            InfoAboutLotRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, InfoAboutLotRequest.class);
            ServiceUtils.checkInfoSomeLotRequest(dtoRequest);
            Lot lot = buyerDao.getLot(dtoRequest.getIdSeller(), dtoRequest.getIdLot());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(lot));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getMessage());
        }

    }

    // REVU где передача токена ?
    public ServerResponse takeInfoAboutAllLotsByCategory(String token, String requestJsonString) throws JsonSyntaxException, ServerException {
        try {
            Buyer buyer = getSellerByToken(token);
            InfoAboutLotsByCategory dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, InfoAboutLotsByCategory.class);
            ServiceUtils.checkInfoAllLotsRequest(dtoRequest);
            List<Lot> list = buyerDao.getLotListByCategory(dtoRequest.getIdCategory());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(list));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getMessage());
        }

    }

    // REVU где передача токена ?
    public ServerResponse addPrice(String token, String requestJsonString) throws JsonSyntaxException, ServerException {
        try {
            Buyer buyer = getSellerByToken(token);
            AddPriceDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, AddPriceDtoRequest.class);
            ServiceUtils.checkAddPrice(dtoRequest);
            buyerDao.addPrice(dtoRequest.getBuyerID(), dtoRequest.getValue(), dtoRequest.getLotID());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getMessage());
        }

    }

    // REVU где передача токена ?
    public ServerResponse deletePrice(String token, String requestJsonString) throws JsonSyntaxException, ServerException {
        try {
            Buyer buyer = getSellerByToken(token);
            DeleteLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, DeleteLotDtoRequest.class);
            ServiceUtils.checkDeleteLotRequest(dtoRequest);
            buyerDao.deletePrice(dtoRequest.getLotID());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getMessage());
        }
    }

    private Buyer getSellerByToken(String token) throws ServerException {
        if (token == null){
            throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
        }
        UserDtoResponse userDtoResponse = userDao.getUserByToken(UUID.fromString(token));
        User user = UserMapperFromLogin.MAPPER.toUser(userDtoResponse);
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        if (!(user instanceof Buyer)) {
            throw new ServerException(ServerErrorCode.NOT_A_SELLER);
        }
        return (Buyer) user;
    }

}
