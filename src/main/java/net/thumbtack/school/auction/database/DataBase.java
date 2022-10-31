package net.thumbtack.school.auction.database;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.User;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
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
    private BidiMap<UUID, User> userByToken = new DualHashBidiMap<>();

    public void insert(User user) throws UserException {
        if(users.putIfAbsent(user.getLogin(), user) != null) {
            throw new UserException(UserErrorCode.DOUBLE_LOGIN);
        }
    }

    public User get(String login){
        return users.get(login);
    }

    public UUID getToken(String login){
        User user = get(login);
        return userByToken.getKey(user);
    }

    public UUID loginSeller(User user) throws UserException {
        UUID uuid = userByToken.getKey(user);
        if(uuid != null) {
            return uuid;
        }
        UUID token = UUID.randomUUID();
        userByToken.put(token, user);
        return token;
    }

    public UUID loginBuyer(User user) throws UserException {
        UUID uuid = userByToken.getKey(user);
        if(uuid != null) {
            return uuid;
        }
        UUID token = UUID.randomUUID();
        userByToken.put(token, user);
        return token;
    }

    public void logoutBuyer(UUID token) throws UserException {
        if (userByToken.remove(token) == null) {
            throw new UserException(UserErrorCode.SESSION_NOT_FOUND);
        }
    }

    public void logoutSeller(UUID token) throws UserException {
        if (userByToken.remove(token) == null) {
            throw new UserException(UserErrorCode.SESSION_NOT_FOUND);
        }
    }
}

