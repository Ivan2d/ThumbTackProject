package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.dto.request.GetUserByTokenDtoRequest;
import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutDtoRequest;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.dto.response.ErrorDtoResponse;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
import net.thumbtack.school.auction.dto.response.UserDtoResponse;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.mapper.UserMapperFromLogin;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;

import java.util.UUID;

public class UserService {
    private static final UserDao userDao = new UserDaoImpl();
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static final Gson gson = new Gson();

    public ServerResponse login(String requestJsonString) throws ServerException {
        try {
            LoginDtoRequest loginBuyerDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
            ServiceUtils.checkRequest(loginBuyerDtoRequest);
            User user = userDao.get(loginBuyerDtoRequest.getLogin());
            if (user == null || !user.getPassword().equals(loginBuyerDtoRequest.getPassword())) {
                throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            UUID uuid = userDao.login(user);
            LoginDtoResponse loginUserDtoResponse = new LoginDtoResponse(uuid);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(loginUserDtoResponse));
        } catch (ServerException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    public ServerResponse logout(String requestJsonString) throws ServerException
    {
        try {
            LogoutDtoRequest buyerDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, LogoutDtoRequest.class);
            ServiceUtils.checkDeleteLotRequest(buyerDtoRequest);
            userDao.logout(buyerDtoRequest.getToken());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        }
        catch (ServerException e){
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    public UserDtoResponse getUserByToken(String requestJsonString) throws ServerException {
        try {
            GetUserByTokenDtoRequest getUserByTokenDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, GetUserByTokenDtoRequest.class);
            ServiceUtils.checkUserByToken(getUserByTokenDtoRequest);
            return userDao.getUserByToken(getUserByTokenDtoRequest.getUuid());
        }
        catch (ServerException exception){
            throw exception;
        }
    }

}
