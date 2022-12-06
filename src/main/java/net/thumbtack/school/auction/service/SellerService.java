package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.mapper.LotMapperFromDto;
import net.thumbtack.school.auction.mapper.SellerMapperFromRegister;
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

    private SellerDao sellerDao = new SellerDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static final Gson gson = new Gson();

    public ServerResponse registerUser(String requestJsonString) {
        try {
            RegisterDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, RegisterDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
            Seller seller = SellerMapperFromRegister.MAPPER.toSeller(dtoRequest);
            sellerDao.insert(seller);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

    public ServerResponse addLotOnAuction(String token, String requestJsonString) {
        try {
            Seller seller = getSellerByToken(token);
            AddLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, AddLotDtoRequest.class);
            ServiceUtils.checkAddLotRequest(dtoRequest);
            Lot lot = LotMapperFromDto.MAPPER.toLot(dtoRequest);
            sellerDao.addLot(lot);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }


    public ServerResponse deleteLotOnAuction(String token, String requestJsonString)  {
        try {
            Seller seller = getSellerByToken(token);
            DeleteLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, DeleteLotDtoRequest.class);
            ServiceUtils.checkDeleteLotRequest(dtoRequest);
            int id = dtoRequest.getLotId();
            sellerDao.deleteLot(id);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

    private Seller getSellerByToken(String token) throws ServerException {
        if (token == null){
            throw new ServerException(ServerErrorCode.TOKEN_NOT_FOUND);
        }
        User user = userDao.getUserByToken(UUID.fromString(token));
        if (user == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        if (!(user instanceof Seller)) {
            throw new ServerException(ServerErrorCode.NOT_A_SELLER);
        }
        return (Seller) user;
    }

}
