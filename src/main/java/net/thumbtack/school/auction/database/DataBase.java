package net.thumbtack.school.auction.database;
import net.thumbtack.school.auction.dto.response.UserDtoResponse;
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
    private MultiValuedMap<Integer, Lot> lotMultiValuedMapByCategoryId = new HashSetValuedHashMap<>();
    private Map<Integer, Lot> integerLotMap = new HashMap<>();

    private Map<Integer, Price> priceById = new HashMap<>();

    private Map<Integer, Category> categoryById = new HashMap<>();

    private int nextUserId = 1;

    public List<Lot> getListByCategory(int idCategory){
        return (List<Lot>) lotMultiValuedMapByCategoryId.get(idCategory);
    }

    public void insert(User user) throws ServerException {
        if (userByLogin.putIfAbsent(user.getLogin(), user) != null) {
            throw new ServerException(ServerErrorCode.DUPLICATE_LOGIN);
        }
        user.setId(nextUserId++);
        userByID.put(user.getId(), user);
    }

    public User get(String login) {
        return userByLogin.get(login);
    }

    public UserDtoResponse getByToken(UUID uuid) throws ServerException {
        User user = userByToken.get(uuid);
        return new UserDtoResponse(user.getFirstname(), user.getLastname(), user.getLogin());
    }

    public Lot getLotBySeller(int idSeller, int idLot) throws ServerException {
        Seller seller = (Seller) userByID.get(idSeller);
        Lot lot = integerLotMap.get(idLot);
        if(lotsBySeller.containsKey(seller) && lotsBySeller.containsValue(lot)) {
            return lot;
        }
        else {
            throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
        }
    }

    public void addLot(Lot lot) throws ServerException {
        lotsBySeller.put(lot.getSeller(), lot);
        for(Category item: lot.getCategories()){
            lotMultiValuedMapByCategoryId.put(item.getId(), lot);
        }
        integerLotMap.put(lot.getId(), lot);
    }

    public void deleteLot(int ID) throws ServerException {
        integerLotMap.remove(ID);
    }

    public void addPrice(int idBuyer, int value, int idLot){
        User user = userByID.get(idBuyer);
        if(user instanceof Buyer){
           Lot lot = integerLotMap.get(idLot);
           Price price = new Price((Buyer) user, value, lot);
           priceById.put(price.getBid(), price);
        }
    }

    public void deletePrice(int idValue){
        priceById.remove(idValue);
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
}

