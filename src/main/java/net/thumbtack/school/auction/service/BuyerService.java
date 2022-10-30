package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.daoimpl.BuyerDaoImpl;
import net.thumbtack.school.auction.dto.request.LoginBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterSellerDtoRequest;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Buyer;
import net.thumbtack.school.auction.model.Seller;
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
        // REVU а если json с ошибкой ?
        // возникнет JsonSyntaxException
        // лучше сделать шаблонный метод getClassFromJson
        // https://docs.oracle.com/javase/tutorial/extra/generics/methods.html
        // и пусть он внутри ловит JsonSyntaxException,
        // а поймав, выбросит ServerException с ErrorCode.WRONG_JSON

        try {
            RegisterBuyerDtoRequest dtoRequest = gson.fromJson(requestJsonString, RegisterBuyerDtoRequest.class);
            checkRequest(dtoRequest);
            // REVU все верно, но если дальше не хочется такое писать, то посмотрите
            // https://mapstruct.org/
            // а еще можно посмотреть
            // https://projectlombok.org/
            // и их интеграцию
            // https://stackoverflow.com/questions/47676369/mapstruct-and-lombok-not-working-together
            // и жизнь станет прекрасной :-)
            Buyer buyer = new Buyer(dtoRequest.getFirstName(), dtoRequest.getLastName(), dtoRequest.getLogin(), dtoRequest.getPassword());
            buyerDao.insert(buyer);
            EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(emptySuccessDtoResponse));
        }
        catch (JsonSyntaxException c) {
            UserException exception = new UserException(UserErrorCode.WRONG_JSON);
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(exception);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
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

    public static ServerResponse loginBuyer (String requestJsonString) throws UserException {
        try {
        LoginBuyerDtoRequest loginBuyerDtoRequest = gson.fromJson(requestJsonString,LoginBuyerDtoRequest.class);
        checkRequest(loginBuyerDtoRequest);
        User user = buyerDao.get(loginBuyerDtoRequest.getLogin());
        if (user == null || !user.getPassword().equals(loginBuyerDtoRequest.getPassword())) {
            throw new UserException(UserErrorCode.WRONG_LOGIN_OR_PASSWORD);
        }
        LoginBuyerDtoResponse loginUserDtoResponse = new LoginBuyerDtoResponse(buyerDao.loginUser(user);
        return new ServerResponse(CODE_SUCCESS, gson.toJson(loginUserDtoResponse));
        } catch (UserException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    public static ServerResponse logoutBuyer (String requestJsonString) throws UserException {
        ServerResponse response = null;
        Gson gson = new Gson();
        LogoutBuyerDtoRequest lbdr = gson.fromJson(requestJsonString, LogoutBuyerDtoRequest.class);
        try{
            LogoutBuyerDtoResponce logoutBuyerDtoRequest = new LogoutBuyerDtoResponce(buyerDao.logoutUser(lbdr));
            response = logoutBuyerDtoRequest.getResponce();
        } catch (UserException e) {
            return new ServerResponse(400, gson.toJson(e));
        }
        return response;
    }

}
