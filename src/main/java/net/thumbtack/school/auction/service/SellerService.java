package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.*;
import net.thumbtack.school.auction.daoimpl.*;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.mappers.mapstruct.LotMapperFromDto;
import net.thumbtack.school.auction.mappers.mapstruct.SellerMapperFromLogin;
import net.thumbtack.school.auction.model.Category;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.Seller;

import java.util.UUID;


public class SellerService {

    private UserDao userDao = new UserDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private LotDao lotDao = new LotDaoImpl();
    private PriceDao priceDao = new PriceDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static final Gson gson = new Gson();

    public ServerResponse loginSeller(String requestJsonString) {
        try {
            LoginDtoRequest loginDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
            ServiceUtils.checkRequest(loginDtoRequest);
            Seller seller = SellerMapperFromLogin.MAPPER.toSeller(loginDtoRequest);
            seller.setUuid(UUID.randomUUID().toString());
            sellerDao.login(seller);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new TokenDtoResponse(seller.getUuid())));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

    public ServerResponse logoutSeller(String token) {
        try {
            sellerDao.deleteFromSession(token);
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
            lot.setSeller(seller);
            sellerDao.addLotToSeller(seller, lot);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(lot.getId()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }


    public ServerResponse deleteLotOnAuction(String token, String requestJsonString) {
        try {
            Seller seller = getSellerByToken(token);
            DeleteLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, DeleteLotDtoRequest.class);
            ServiceUtils.checkDeleteLotRequest(dtoRequest);
            lotDao.deleteLot(dtoRequest.getId());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse addCategoryToLot(String token, String requestJsonString) {
        try {
            Seller seller = getSellerByToken(token);
            AddCategoryToLotRequest dtoRequest = ServiceUtils.
                    getObjectFromJson(requestJsonString, AddCategoryToLotRequest.class);
            ServiceUtils.checkAddCategoryToLot(dtoRequest);
            categoryDao.addCategoryToLot(dtoRequest.getIdLot(), dtoRequest.getIdCategory());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    public ServerResponse deleteCategoryFromLot(String token, String requestJsonString) {
        try {
            Seller seller = getSellerByToken(token);
            DeleteCategoryFromLotRequest dtoRequest = ServiceUtils.
                    getObjectFromJson(requestJsonString, DeleteCategoryFromLotRequest.class);
            ServiceUtils.checkDeleteCategoryFromLot(dtoRequest);
            categoryDao.deleteCategoryFromLot(dtoRequest.getIdLot(), dtoRequest.getIdCategory());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }

    private Seller getSellerByToken(String token) throws ServerException {
        Seller seller = sellerDao.getByToken(token);
        if (seller == null) {
            throw new ServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        return seller;
    }

    public ServerResponse addCategory(String token, String requestJsonString) {
        try {
            Seller seller = getSellerByToken(token);
            AddCategoryRequest request = ServiceUtils.
                    getObjectFromJson(requestJsonString, AddCategoryRequest.class);
            int integer = categoryDao.insert(new Category(request.getName())).getId();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new AddCategoryResponse(integer)));
        } catch (ServerException e) {
            return new ServerResponse(e);
        }
    }
}
