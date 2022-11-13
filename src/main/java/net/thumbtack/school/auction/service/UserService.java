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
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.mapper.UserMapperFromLogin;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class UserService {
    private static final UserDao userDao = new UserDaoImpl();
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static final Gson gson = new Gson();

    public ServerResponse login(String requestJsonString) throws UserException {
        try {
            LoginDtoRequest loginBuyerDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
            checkRequest(loginBuyerDtoRequest);
            User user = UserMapperFromLogin.MAPPER.toUser(loginBuyerDtoRequest);
            if (user == null || !user.getPassword().equals(loginBuyerDtoRequest.getPassword())) {
                throw new UserException(UserErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            UUID uuid = userDao.login(user);
            LoginDtoResponse loginUserDtoResponse = new LoginDtoResponse(uuid);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(loginUserDtoResponse));
        } catch (UserException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    public ServerResponse logout(String requestJsonString) throws UserException
    {
        try {
            LogoutDtoRequest buyerDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, LogoutDtoRequest.class);
            userDao.logout(buyerDtoRequest.getToken());
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        }
        catch (UserException e){
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    public User getUserByToken(String requestJsonString) throws UserException {
        try {
            GetUserByTokenDtoRequest getUserByTokenDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, GetUserByTokenDtoRequest.class);
            return userDao.getUserByToken(getUserByTokenDtoRequest.getUuid());
        }
        catch (UserException e){
            throw e;
        }
    }

    private void checkRequest(LoginDtoRequest request) throws UserException {
        if(request.getLogin() == null || StringUtils.isEmpty(request.getLogin()) || request.getLogin().length() <= MIN_LOGIN_LEN) {
            throw new UserException(UserErrorCode.EMPTY_LOGIN);
        }
        if(request.getPassword() == null || StringUtils.isEmpty(request.getPassword()) || request.getPassword().length() <= MIN_PASSWORD_LEN) {
            throw new UserException(UserErrorCode.EMPTY_PASSWORD);
        }
    }
}
