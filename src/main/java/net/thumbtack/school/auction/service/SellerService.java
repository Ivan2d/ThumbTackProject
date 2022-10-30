package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.daoimpl.SellerDaoImpl;
import net.thumbtack.school.auction.dto.request.LoginSellerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutSellerDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterSellerDtoRequest;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
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
        // REVU а если json с ошибкой ?
        // возникнет JsonSyntaxException
        // лучше сделать шаблонный метод getClassFromJson
        // https://docs.oracle.com/javase/tutorial/extra/generics/methods.html
        // и пусть он внутри ловит JsonSyntaxException,
        // а поймав, выбросит ServerException с ErrorCode.WRONG_JSON

        try {
            RegisterSellerDtoRequest dtoRequest = gson.fromJson(requestJsonString, RegisterSellerDtoRequest.class);
            checkRequest(dtoRequest);
            // REVU все верно, но если дальше не хочется такое писать, то посмотрите
            // https://mapstruct.org/
            // а еще можно посмотреть
            // https://projectlombok.org/
            // и их интеграцию
            // https://stackoverflow.com/questions/47676369/mapstruct-and-lombok-not-working-together
            // и жизнь станет прекрасной :-)
            Seller seller = new Seller(dtoRequest.getFirstName(), dtoRequest.getLastName(), dtoRequest.getLogin(), dtoRequest.getPassword());
            UUID uuid = sellerDao.insert(seller);
            RegisterSellerDtoResponce registerUserDtoResponse = new RegisterSellerDtoResponce(uuid);
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

    public static ServerResponse loginSeller(String requestJsonString) throws UserException {
        try {
            LoginSellerDtoRequest loginSellerDtoRequest = gson.fromJson(requestJsonString, LoginSellerDtoRequest.class);
            UUID uuid = sellerDao.loginUser(loginSellerDtoRequest);
            LoginBuyerDtoResponse loginUserDtoResponse = new LoginBuyerDtoResponse(uuid);
            return new ServerResponse(200, gson.toJson(loginUserDtoResponse));
        } catch (UserException e) {
            return new ServerResponse(400, gson.toJson(e));
        }
    }

    public static ServerResponse logoutSeller(String requestJsonString) throws UserException {
        Gson gson = new Gson();
        LogoutSellerDtoRequest logoutSellerDtoRequest = gson.fromJson(requestJsonString, LogoutSellerDtoRequest.class);
        LogoutSellerDtoResponce logoutBuyerDtoRequest = new LogoutSellerDtoResponce(new ServerResponse(200, logoutSellerDtoRequest.getToken().toString()));
        return logoutBuyerDtoRequest.getResponce();
    }

}
