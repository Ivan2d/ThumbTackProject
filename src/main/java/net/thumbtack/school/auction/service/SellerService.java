package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.mapper.LotMapperFromDto;
import net.thumbtack.school.auction.mapper.SellerMapperFromRegister;
import net.thumbtack.school.auction.mapper.UserMapperFromLogin;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.daoimpl.SellerDaoImpl;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Seller;

import java.util.UUID;

public class SellerService {

    private static SellerDao sellerDao = new SellerDaoImpl();
    private static UserDao userDao = new UserDaoImpl();
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
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getMessage());
        }
    }

    // REVU где передача токена ?
    public ServerResponse addLotOnAuction(UUID token, String requestJsonString) throws ServerException {
        try {
            AddLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, AddLotDtoRequest.class);
            ServiceUtils.checkAddLotRequest(dtoRequest);
            Lot lot = LotMapperFromDto.MAPPER.toLot(dtoRequest);
            sellerDao.addLot(lot);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getMessage());
        }
    }

    private Seller getSellerByToken(UUID token) throws ServerException {
        UserDtoResponse userDtoResponse = userDao.getUserByToken(token);
        User user = UserMapperFromLogin.MAPPER.toUser(userDtoResponse);
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        if (!(user instanceof Seller)) {
            throw new ServerException(ServerErrorCode.NOT_A_SELLER);
        }
        return (Seller) user;
    }

    // REVU где передача токена ?
    public ServerResponse deleteLotOnAuction(String requestJsonString) throws ServerException {
        try {
            DeleteLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, DeleteLotDtoRequest.class);
            ServiceUtils.checkDeleteLotRequest(dtoRequest);
            int ID = dtoRequest.getLotID();
            sellerDao.deleteLot(ID);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getMessage());
        }
    }

}
