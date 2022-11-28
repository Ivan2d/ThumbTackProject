package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.mapper.BuyerMapperFromRegister;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.daoimpl.BuyerDaoImpl;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Buyer;

import java.util.List;

public class BuyerService {
    private static BuyerDao buyerDao = new BuyerDaoImpl();
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
        }
        catch (UserException e) {
            return new ServerResponse(CODE_ERROR, gson.toJson(new ErrorDtoResponse(e)));
        }
    }

    // REVU где передача токена ?
    public ServerResponse takeInfoAboutSomeLot(String requestJsonString) throws JsonSyntaxException, UserException {
        // REVU и тут try и тут валидация, и тут catch
        InfoAboutLotRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, InfoAboutLotRequest.class);
        Lot lot = buyerDao.getLot(dtoRequest.getIdOfSeller(), dtoRequest.getIdOfLot());
        return new ServerResponse(CODE_SUCCESS, gson.toJson(lot));
    }

    // REVU где передача токена ?
    public ServerResponse takeInfoAboutAllLotsByCategory(String requestJsonString) throws JsonSyntaxException, UserException {
        InfoAboutLotsByCategory dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, InfoAboutLotsByCategory.class);
        List<Lot> list = buyerDao.getLotListByCategory(dtoRequest.getIdCategory());
        return new ServerResponse(CODE_SUCCESS, gson.toJson(list));
    }

    // REVU где передача токена ?
    public ServerResponse addPrice(String requestJsonString) throws JsonSyntaxException, UserException {
        AddPriceDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, AddPriceDtoRequest.class);
        buyerDao.addPrice(dtoRequest.getBuyerID(), dtoRequest.getValue(), dtoRequest.getLotID());
        return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
    }

    // REVU где передача токена ?
    public ServerResponse deletePrice(String requestJsonString) throws JsonSyntaxException, UserException {
        DeleteLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, DeleteLotDtoRequest.class);
        buyerDao.deletePrice(dtoRequest.getLotID());
        return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
    }
}
