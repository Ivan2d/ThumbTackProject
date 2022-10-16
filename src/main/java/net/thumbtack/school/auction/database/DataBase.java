package net.thumbtack.school.auction.database;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.TokenBox;
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
    private Map<UUID, TokenBox> tokenBoxes = new HashMap<>();

    public UUID insert (User user) throws UserException {
        if (againLogin(user)) {
            throw new UserException(UserErrorCode.DOUBLE_LOGIN);
        }
        users.put(user.getLogin(),user);
        UUID token = UUID.randomUUID();
        TokenBox tokenBox = new TokenBox(token,user.getLogin());
        tokenBoxes.put(token, tokenBox);
        return token;
    }

    public boolean againLogin (User user) {
        return users.containsKey(user.getLogin());
    }

}
