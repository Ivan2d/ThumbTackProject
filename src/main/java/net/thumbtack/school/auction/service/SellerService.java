package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.daoimpl.SellerDaoImpl;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class SellerService {
    private static SellerDao sellerDao = new SellerDaoImpl();
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static Gson gson = new Gson();

    public ServerResponse registerUser(String requestJsonString) throws JsonSyntaxException {
        try {
            RegisterSellerDtoRequest dtoRequest = Service.getObjectFromJson(requestJsonString, RegisterSellerDtoRequest.class);
            checkRequest(dtoRequest);
            Seller seller = new Seller(dtoRequest.getFirstName(), dtoRequest.getLastName(), dtoRequest.getLogin(), dtoRequest.getPassword());
            sellerDao.insert(seller);
            EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(emptySuccessDtoResponse));
        }
        catch (UserException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    public void checkRequest(RegisterSellerDtoRequest request) throws UserException {
        if (request.getFirstName() == null || StringUtils.isEmpty(request.getFirstName()))
            throw new UserException(UserErrorCode.EMPTY_FIRST_NAME);
        if (request.getLastName() == null || StringUtils.isEmpty(request.getLastName()))
            throw new UserException(UserErrorCode.EMPTY_LAST_NAME);
        if (request.getLogin() == null || StringUtils.isEmpty(request.getLogin()))
            throw new UserException(UserErrorCode.EMPTY_LOGIN);
        if (request.getLogin().length() < MIN_LOGIN_LEN)
            throw new UserException(UserErrorCode.SHORT_LOGIN);
        if (request.getPassword() == null || StringUtils.isEmpty(request.getPassword()))
            throw new UserException(UserErrorCode.EMPTY_PASSWORD);
        if (request.getPassword().length() < MIN_PASSWORD_LEN)
            throw new UserException(UserErrorCode.SHORT_PASSWORD);
    }

    private void checkRequest(LoginSellerDtoRequest request) throws UserException {
        if(request.getLogin() == null || StringUtils.isEmpty(request.getLogin()))
            throw new UserException(UserErrorCode.EMPTY_LOGIN);
        if(request.getPassword() == null || StringUtils.isEmpty(request.getPassword()))
            throw new UserException(UserErrorCode.EMPTY_PASSWORD);
    }

    public ServerResponse loginSeller(String requestJsonString) throws UserException {
        try {
            LoginSellerDtoRequest loginSellerDtoRequest = Service.getObjectFromJson(requestJsonString, LoginSellerDtoRequest.class);
            checkRequest(loginSellerDtoRequest);
            User user = sellerDao.get(loginSellerDtoRequest.getLogin());
            if (user == null || !user.getPassword().equals(loginSellerDtoRequest.getPassword())) {
                throw new UserException(UserErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            UUID uuid = sellerDao.loginUser(user);
            LoginSellerDtoResponce loginUserDtoResponse = new LoginSellerDtoResponce(uuid);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(loginUserDtoResponse));
        } catch (UserException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    public ServerResponse logoutSeller(String requestJsonString) throws UserException {
        try {
            LogoutSellerDtoRequest buyerDtoRequest = Service.getObjectFromJson(requestJsonString, LogoutSellerDtoRequest.class);
            sellerDao.logoutUser(buyerDtoRequest.getToken());
            if (buyerDtoRequest.getToken() == null) {
                throw new UserException(UserErrorCode.TOKEN_NOT_FOUND);
            }
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        }
        catch (UserException e){
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

}
