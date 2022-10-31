package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.daoimpl.BuyerDaoImpl;
import net.thumbtack.school.auction.dto.request.LoginBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterBuyerDtoRequest;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class BuyerService {
    private static BuyerDao buyerDao = new BuyerDaoImpl();
    private static Gson gson = new Gson();
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;

    public ServerResponse registerUser(String requestJsonString) throws JsonSyntaxException {
        try {
            RegisterBuyerDtoRequest dtoRequest = Service.getObjectFromJson(requestJsonString, RegisterBuyerDtoRequest.class);
            checkRequest(dtoRequest);
            Buyer buyer = new Buyer(dtoRequest.getFirstName(), dtoRequest.getLastName(), dtoRequest.getLogin(), dtoRequest.getPassword());
            buyerDao.insert(buyer);
            EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(emptySuccessDtoResponse));
        }
        catch (UserException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    private void checkRequest(RegisterBuyerDtoRequest request) throws UserException {
        if(request.getFirstName() == null || StringUtils.isEmpty(request.getFirstName()))
            throw new UserException(UserErrorCode.EMPTY_FIRST_NAME);
        if(request.getLastName() == null || StringUtils.isEmpty(request.getLastName()))
            throw new UserException(UserErrorCode.EMPTY_LAST_NAME);
        if(request.getLogin() == null || StringUtils.isEmpty(request.getLogin()))
            throw new UserException(UserErrorCode.EMPTY_LOGIN);
        if (request.getLogin().length() < MIN_LOGIN_LEN)
            throw new UserException(UserErrorCode.SHORT_LOGIN);
        if(request.getPassword() == null || StringUtils.isEmpty(request.getPassword()))
            throw new UserException(UserErrorCode.EMPTY_PASSWORD);
        if (request.getPassword().length() < MIN_PASSWORD_LEN)
            throw new UserException(UserErrorCode.SHORT_PASSWORD);
    }

    private void checkRequest(LoginBuyerDtoRequest request) throws UserException {
        if(request.getLogin() == null || StringUtils.isEmpty(request.getLogin()))
            throw new UserException(UserErrorCode.EMPTY_LOGIN);
        if(request.getPassword() == null || StringUtils.isEmpty(request.getPassword()))
            throw new UserException(UserErrorCode.EMPTY_PASSWORD);
    }

    public ServerResponse loginBuyer (String requestJsonString) throws UserException {
        try {
        LoginBuyerDtoRequest loginBuyerDtoRequest = Service.getObjectFromJson(requestJsonString, LoginBuyerDtoRequest.class);
        checkRequest(loginBuyerDtoRequest);
        User user = buyerDao.get(loginBuyerDtoRequest.getLogin());
        if (user == null || !user.getPassword().equals(loginBuyerDtoRequest.getPassword())) {
            throw new UserException(UserErrorCode.WRONG_LOGIN_OR_PASSWORD);
        }
        UUID uuid = buyerDao.loginUser(user);
        LoginBuyerDtoResponse loginUserDtoResponse = new LoginBuyerDtoResponse(uuid);
        return new ServerResponse(CODE_SUCCESS, gson.toJson(loginUserDtoResponse));
        } catch (UserException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    public ServerResponse logoutBuyer (String requestJsonString) throws UserException
    {
        try {
            Gson gson = new Gson();
            LogoutBuyerDtoRequest buyerDtoRequest = Service.getObjectFromJson(requestJsonString, LogoutBuyerDtoRequest.class);
            buyerDao.logoutUser(buyerDtoRequest);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        }
        catch (UserException e){
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

}
