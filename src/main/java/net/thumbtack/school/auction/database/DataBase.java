package net.thumbtack.school.auction.database;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.*;
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

    private Map<Integer, User> userByID = new HashMap<>();
    private Map<String, User> userByLogin = new HashMap<>();
    private BidiMap<UUID, User> userByToken = new DualHashBidiMap<>();

    private MultiValuedMap<Seller, Lot> lotsBySeller = new HashSetValuedHashMap<>();
    private MultiValuedMap<Integer, Lot> lotMultiValuedMapByCategoryId = new HashSetValuedHashMap<>();
    private Map<Integer, Lot> integerLotMap = new HashMap<>();

    private Map<Integer, Price> priceById = new HashMap<>();

    private Map<Integer, Category> categoryById = new HashMap<>();

    private int nextUserId = 1;

    public List<Lot> getListByCategory(int idCategory){
        return (List<Lot>) lotMultiValuedMapByCategoryId.get(idCategory);
    }

    public void insert(User user) throws UserException {
        if (userByLogin.putIfAbsent(user.getLogin(), user) != null) {
            throw new UserException(UserErrorCode.DUPLICATE_LOGIN);
        }
        user.setId(nextUserId++);
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

    public void deleteLot(int ID) throws UserException {
        integerLotMap.remove(ID);
    }

    public void addPrice(int idBuyer, int value, int idLot){
        User user = userByID.get(idBuyer);
        if(user instanceof Buyer){
           Lot lot = integerLotMap.get(idLot);
           Price price = new Price((Buyer) user, value, lot);
           priceById.put(price.getId(), price);
        }
    }

    public void deletePrice(int idValue){
        priceById.remove(idValue);
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

