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
    // REVU просто lotsByCategoryId
    // тип я и сам могу посмотреть по описанию
    private MultiValuedMap<Integer, Lot> lotMultiValuedMapByCategoryId = new HashSetValuedHashMap<>();
    // REVU lotById
    private Map<Integer, Lot> integerLotMap = new HashMap<>();

    private Map<Integer, Price> priceById = new HashMap<>();

    private Map<Integer, Category> categoryById = new HashMap<>();

    private int nextUserId = 1;
    private int nextLotId = 1;

    // REVU хм. Он у Вас HashSetValuedHashMap
    // так что там HashSet<Lot>
    // и преобразование его к List<Lot>  не пройдет
    // да и не надо
    // пусть метод возвращает Collection<Lot>, как и сказано в get
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

    // REVU getByLogin
    public User get(String login) {
        return userByLogin.get(login);
    }

    public User getByToken(UUID uuid) throws ServerException {
        return userByToken.get(uuid);
    }

    // REVU хм.
    // если есть idLot - зачем передавать сюда еще idSeller ?
    // по лоту и так должно быть можно получить его продавца
    // Внутри Lot есть Seller
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
    //    for(Category item: lot.getCategories()){
    //        lotMultiValuedMapByCategoryId.put(item.getId(), lot);
    //    }
        lot.setId(nextLotId++);
        integerLotMap.put(lot.getId(), lot);
    }

    // REVU int id
    public void deleteLot(int ID) throws ServerException {
        // REVU не надо containsKey, remove сама скажет
        // вообще containsKey нужен только тогда, когда мы хотим лишь проверить и ничего не намерены менять
        if(!integerLotMap.containsKey(ID) ){
            throw new ServerException(ServerErrorCode.ID_NOT_EXIST);
        }
        integerLotMap.remove(ID);
    }

    // REVU в методы выше Вы передавали объекты
    // а тут почему-то их id
    // лучше было бы в сервисе получить объекты по id, там и проверить
    // а сюда передать объекты
    // public void addPrice(Buyer buyer, Lot lot, Price price) throws ServerException {
    // и в нем останется priceById.put(price.getBid(), price);
    // после чего станет ясно, что передавать сюда Buyer и Lot незачем
    // так как они есть в Price
    // и метод будет выглядеть так
    // public void addPrice(Price price) throws ServerException {
    // и все станет предельно ясно
    // БД не занимается логикой и проверками, БД только хранилище
    // и тем более не ее дело выполнять new
    public void addPrice(int idBuyer, int value, int idLot) throws ServerException {
        User user = userByID.get(idBuyer);
        if(user instanceof Buyer){
            if(!integerLotMap.containsKey(idLot)){
                throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
            }
           Lot lot = integerLotMap.get(idLot);
           Price price = new Price((Buyer) user, value, lot);
           priceById.put(price.getBid(), price);
        }
    }

    // REVU аналогично, передайте сюда Price
    // а кстати, нужен ли этот метод
    // разве можно удалить ставку ?
    public void deletePrice(int idValue) throws ServerException{
        if(priceById.remove(idValue) == null){
            throw new ServerException(ServerErrorCode.PRICE_NOT_FOUND);
        }
    }

    // REVU упорядочите методы в классе
    // сначала методы с юзерами, потом с лотами и т.д.
    // читать будет легче
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

