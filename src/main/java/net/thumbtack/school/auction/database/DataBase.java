package net.thumbtack.school.auction.database;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
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

    private MultiValuedMap<Integer, Lot> lotsByCategoryId = new HashSetValuedHashMap<>();
    private Map<Integer, Lot> lotById = new HashMap<>();

    private Map<Integer, Price> priceById = new HashMap<>();

    private Map<Integer, Category> categoryById = new HashMap<>();

    private int nextUserId = 1;
    private int nextLotId = 1;


    public void insert(User user) throws ServerException {
        if (userByLogin.putIfAbsent(user.getLogin(), user) != null) {
            throw new ServerException(ServerErrorCode.DUPLICATE_LOGIN);
        }
        user.setId(nextUserId++);
        userByID.put(user.getId(), user);
    }

    public UUID login(User user) throws ServerException {
        UUID uuid = userByToken.getKey(user);
        if(uuid != null) {
            return uuid;
        }
        UUID token = UUID.randomUUID();
        userByToken.put(token, user);
        return token;
    }

    public void logout(UUID token) throws ServerException {
        if (userByToken.remove(token) == null) {
            throw new ServerException(ServerErrorCode.SESSION_NOT_FOUND);
        }
    }

    public User getByLogin(String login) {
        return userByLogin.get(login);
    }

    public User getByToken(UUID uuid) throws ServerException {
        return userByToken.get(uuid);
    }

    public void addLot(Lot lot) throws ServerException {
        lotsBySeller.put(lot.getSeller(), lot);
        //    for(Category item: lot.getCategories()){
        //        lotMultiValuedMapByCategoryId.put(item.getId(), lot);
        //    }
        lotById.put(lot.getId(), lot);
        lot.setId(nextLotId++);
    }

    public void deleteLot(int id) throws ServerException {
        if(lotById.remove(id) == null){
            throw new ServerException(ServerErrorCode.ID_NOT_EXIST);
        }
    }

    public Lot getLotBySeller(int idLot) throws ServerException {
        Lot lot = lotById.get(idLot);
        if(lotsBySeller.containsValue(lot)) {
            return lot;
        }
        else {
            throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
        }
    }

    public Collection<Lot> getListByCategory(int idCategory){
        return lotsByCategoryId.get(idCategory);
    }

    public void addPrice(Price price) {
           priceById.put(price.getBid(), price);
    }

    public void clear(){
        userByLogin.clear();
        userByToken.clear();
        userByID.clear();
        lotsBySeller.clear();
        lotById.clear();
        lotsByCategoryId.clear();
        priceById.clear();
        categoryById.clear();
    }

}

