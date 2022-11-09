package net.thumbtack.school.auction.database;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.model.User;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class DataBase {

    private static DataBase ourInstance = new DataBase();
    public static DataBase getInstance() {
        return ourInstance;
    }

    private Map<Integer, User> userByID = new HashMap<>();
    private Map<String, User> userByLogin = new HashMap<>();
    private BidiMap<UUID, User> userByToken = new DualHashBidiMap<>();

    private MultiValuedMap<Seller, Lot> lotMultiValuedMap = new HashSetValuedHashMap<>();

    public void insert(User user) throws UserException {
        if(userByLogin.putIfAbsent(user.getLogin(), user) != null) {
            throw new UserException(UserErrorCode.DOUBLE_LOGIN);
        }
        userByLogin.put(user.getLogin(), user);
    }

    public User get(String login){
        return userByLogin.get(login);
    }

    public User getByToken(UUID uuid) throws UserException {
        // REVU не надо containsKey, get сама скажет
        if(!userByToken.containsKey(uuid)){
            throw new UserException(UserErrorCode.TOKEN_NOT_FOUND);
        }
        return userByToken.get(uuid);
    }

    public void addLot(Lot lot) throws UserException {
        // REVU странная проверка. Зачем null передавать ? Не должен сервис null передавать
        // и как он может быть NOT_FOUND, если мы его добавляем ?
        if(lot == null){ throw new UserException(UserErrorCode.LOT_NOT_FOUND);}
        lotMultiValuedMap.put(lot.getSeller(), lot);
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

