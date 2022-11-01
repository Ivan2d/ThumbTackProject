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

    private Map<Integer, User> userByID = new HashMap<>();
    // REVU userByLogin
    private Map<String, User> users = new HashMap<>();
    private BidiMap<UUID, User> userByToken = new DualHashBidiMap<>();

    public void insert(User user) throws UserException {
        if(users.putIfAbsent(user.getLogin(), user) != null) {
            throw new UserException(UserErrorCode.DOUBLE_LOGIN);
        }
        users.put(user.getLogin(), user);
    }

    public User get(String login){
        return users.get(login);
    }

    public User getByToken(UUID uuid) throws UserException {
        if(!userByToken.containsKey(uuid)){
            throw new UserException(UserErrorCode.TOKEN_NOT_FOUND);
        }
        return userByToken.get(uuid);
    }



    public UUID login(User user) throws UserException {
        UUID uuid = userByToken.getKey(user);
        if(uuid != null) {
            return uuid;
        }
        UUID token = UUID.randomUUID();
        userByToken.put(token, user);
        return token;
    }

    public void logout(UUID token) throws UserException {
        if (userByToken.remove(token) == null) {
            throw new UserException(UserErrorCode.SESSION_NOT_FOUND);
        }
    }
}

