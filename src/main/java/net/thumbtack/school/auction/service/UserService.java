package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.dao.UserDao;
import net.thumbtack.school.auction.daoimpl.UserDaoImpl;
import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;

import java.util.UUID;

public class UserService {
    private UserDao userDao = new UserDaoImpl();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static final Gson gson = new Gson();

    public ServerResponse login(String requestJsonString) {
        try {
            LoginDtoRequest loginBuyerDtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, LoginDtoRequest.class);
            ServiceUtils.checkRequest(loginBuyerDtoRequest);
            User user = userDao.getByLogin(loginBuyerDtoRequest.getLogin());
            if (user == null || !user.getPassword().equals(loginBuyerDtoRequest.getPassword())) {
                throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            UUID uuid = userDao.login(user);
            LoginDtoResponse loginUserDtoResponse = new LoginDtoResponse(uuid);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(loginUserDtoResponse));
        } catch (ServerException e) {
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

    public ServerResponse logout(UUID token)
    {
        try {
            userDao.logout(token);
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
        }
        catch (ServerException e){
            return new ServerResponse(CODE_ERROR, e.getUserErrorCode().getErrorString());
        }
    }

}
