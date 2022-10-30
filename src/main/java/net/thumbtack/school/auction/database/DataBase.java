package net.thumbtack.school.auction.database;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dto.request.LoginBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.LoginSellerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutSellerDtoRequest;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataBase {

    private static DataBase ourInstance = new DataBase();

    public static DataBase getInstance() {
        return ourInstance;
    }

    private Map<String, User> users = new HashMap<>();
    private Map<Integer, User> userByID = new HashMap<>();
    private Map<UUID, User> userByToken = new HashMap<>();

    public UUID insert(User user) throws UserException {
        users.putIfAbsent(user.getLogin(), user);
        UUID token = UUID.randomUUID();
        userByToken.put(token, user);
        return token;
    }

    public UUID loginSeller(LoginSellerDtoRequest dtoRequest) throws UserException {
        User user = users.get(dtoRequest.getLogin());
        if (user == null || !user.getPassword().equals(dtoRequest.getPassword()))
            throw new UserException(UserErrorCode.WRONG_LOGIN_OR_PASSWORD);
        UUID token = UUID.randomUUID();
        userByToken.putIfAbsent(token, user);
        return token;
    }

    public UUID loginBuyer(LoginBuyerDtoRequest dtoRequest) throws UserException {
        User user = users.get(dtoRequest.getLogin());
        if (user == null || !user.getPassword().equals(dtoRequest.getPassword()))
            throw new UserException(UserErrorCode.WRONG_LOGIN_OR_PASSWORD);
        UUID token = UUID.randomUUID();
        userByToken.putIfAbsent(token, user);
        return token;
    }

    public ServerResponse logoutBuyer(LogoutBuyerDtoRequest dtoRequest) {
        UUID token = dtoRequest.getToken();
        userByToken.remove(token);
        return new ServerResponse(200, token.toString());
    }

    public ServerResponse logoutSeller(LogoutSellerDtoRequest dtoRequest) {
        UUID token = dtoRequest.getToken();
        userByToken.remove(token);
        return new ServerResponse(200, token.toString());
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<Integer, User> getUserByID() {
        return userByID;
    }

    public Map<UUID, User> getUserByToken() {
        return userByToken;
    }
}

