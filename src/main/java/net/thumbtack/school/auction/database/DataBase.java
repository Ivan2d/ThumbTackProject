package net.thumbtack.school.auction.database;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Category;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.model.User;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.util.*;

public class DataBase {

    private static DataBase ourInstance = new DataBase();

    public static DataBase getInstance() {
        return ourInstance;
    }

    private BidiMap<Integer, User> userByID = new DualHashBidiMap<>();
    private Map<String, User> userByLogin = new HashMap<>();
    private BidiMap<UUID, User> userByToken = new DualHashBidiMap<>();
    private Map<Integer, Lot> integerLotMap = new HashMap<>();
    private MultiValuedMap<Seller, Lot> lotMultiValuedMap = new HashSetValuedHashMap<>();
    private MultiValuedMap<Integer, Lot> lotMultiValuedMapByCategoryId = new HashSetValuedHashMap<>();
    private Map<Integer, Category> categoryById = new HashMap<>();

    public List<Lot> getListByCategory(int idCategory){
        return lotMultiValuedMapByCategoryId.get(idCategory).stream().toList();
    }

    public void insert(User user) throws UserException {
        if (userByLogin.putIfAbsent(user.getLogin(), user) != null) {
            throw new UserException(UserErrorCode.DUPLICATE_LOGIN);
        }
        userByLogin.put(user.getLogin(), user);
        userByID.put(user.getId(), user);
    }

    public User get(String login) {
        return userByLogin.get(login);
    }

    public User getByToken(UUID uuid) throws UserException {
        return userByToken.get(uuid);
    }

    public Lot getLotBySeller(int idSeller, int idLot) throws UserException {
        Seller seller = (Seller) userByID.get(idSeller);
        Lot lot = integerLotMap.get(idLot);
        if(lotMultiValuedMap.containsKey(seller) && lotMultiValuedMap.containsValue(lot)) {
            return lot;
        }
        else {
            throw new UserException(UserErrorCode.LOT_NOT_FOUND);
        }
    }

    public void addLot(Lot lot) throws UserException {
        lotMultiValuedMap.put(lot.getSeller(), lot);
        for(Category item: lot.getCategories()){
            lotMultiValuedMapByCategoryId.put(item.getId(), lot);
        }
        integerLotMap.put(lot.getId(), lot);
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

