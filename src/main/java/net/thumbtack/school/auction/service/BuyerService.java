package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.mapper.BuyerMapperFromRegister;
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
    // REVU нет, не надо static
    // кто его знает, вдруг еще один экземпляр Dao понадобится
    // static вообще редк нужен (кроме констант)
    // и должны быть серьезные причины для его употребления
    private static BuyerDao buyerDao = new BuyerDaoImpl();
    private static UserDao userDao = new UserDaoImpl();
    // REVU тоже не надо static
    // у нас нет потоков, а если бы были, то 2 потока использовали бы один и тот же экземпляр Gson
    // а он вроде как потокобезопасен (то есть можно из нескольких потоков) по документации
    // а в то же время пишут, что могут быть проблемы с потоками
    // зачем они Вам нужны ? Проще сделать обычное поле
    private static Gson gson = new Gson();
    // а вот эти действительно static. Константы.
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
            // REVU а так можно ?
            // return new ServerResponse(e);
            // и пусть ctor ServerResponse по ServerException все и делает
            // не нужны тут подробности, скрывайте их
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

    // REVU где передача токена ?
    // REVU take - взять что-то. Тут get
    // About и Some - лишнее
    // getLotInfo
    public ServerResponse takeInfoAboutSomeLot(String token, String requestJsonString) {
        try {
            Buyer buyer = getBuyerByToken(token);
            // REVU GetLotInfoRequest
            InfoAboutLotRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, InfoAboutLotRequest.class);
            ServiceUtils.checkInfoSomeLotRequest(dtoRequest);
            Lot lot = buyerDao.getLot(dtoRequest.getIdSeller(), dtoRequest.getIdLot());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(lot));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }

    }

    public ServerResponse takeInfoAboutAllLotsByCategory(String token, String requestJsonString) {
        try {
            Buyer buyer = getBuyerByToken(token);
            InfoAboutLotsByCategory dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, InfoAboutLotsByCategory.class);
            ServiceUtils.checkInfoAllLotsRequest(dtoRequest);
            List<Lot> list = buyerDao.getLotListByCategory(dtoRequest.getIdCategory());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(list));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }

    }

    public ServerResponse addPrice(String token, String requestJsonString) {
        try {
            Buyer buyer = getBuyerByToken(token);
            AddPriceDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, AddPriceDtoRequest.class);
            ServiceUtils.checkAddPrice(dtoRequest);
            buyerDao.addPrice(dtoRequest.getBuyerID(), dtoRequest.getValue(), dtoRequest.getLotID());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }

    }

    public ServerResponse deletePrice(String token, String requestJsonString) {
        try {
            Buyer buyer = getBuyerByToken(token);
            DeleteLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, DeleteLotDtoRequest.class);
            ServiceUtils.checkDeleteLotRequest(dtoRequest);
            buyerDao.deletePrice(dtoRequest.getLotID());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
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
