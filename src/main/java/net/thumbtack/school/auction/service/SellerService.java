package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.mapper.LotMapperFromDto;
import net.thumbtack.school.auction.mapper.SellerMapperFromRegister;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.daoimpl.SellerDaoImpl;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;

import java.util.UUID;

public class SellerService {

    private static SellerDao sellerDao = new SellerDaoImpl();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static Gson gson = new Gson();

    public ServerResponse registerUser(String requestJsonString) throws JsonSyntaxException {
        try {
            RegisterDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, RegisterDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
            Seller seller = SellerMapperFromRegister.MAPPER.toSeller(dtoRequest);
            sellerDao.insert(seller);
            EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(emptySuccessDtoResponse));
        }
        catch (UserException e) {
            // REVU а так можно ?
            // return new ServerResponse(e);
            // и пусть конструктор ServerResponse и разбирается
            // все логично. Сделай ServerResponse, данные возьми из исключения
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    // REVU где передача токена ?
    public ServerResponse addLotOnAuction(UUID token, String requestJsonString) throws UserException {
        // REVU и тут try и тут валидация, и тут catch
        Seller seller = getSellerByToken(token);
            AddLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, AddLotDtoRequest.class);
            Lot lot = LotMapperFromDto.MAPPER.toLot(dtoRequest);
            sellerDao.addLot(lot);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
    }

    private Seller getSellerByToken(UUID token) {
        User user = sellerDao.getUserByToken(token);
        if(user == null) { throw...}
        if(!(user instanceof Seller)) {
            throw ...
        }
        return (Seller) user;
    }

    // REVU где передача токена ?
    public ServerResponse deleteLotOnAuction(String requestJsonString) throws UserException {
        // REVU и тут то же
            DeleteLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, DeleteLotDtoRequest.class);
            int ID = dtoRequest.getLotID();
            sellerDao.deleteLot(ID);
            return new ServerResponse(CODE_ERROR, gson.toJson(new EmptySuccessDtoResponse()));
    }

}
