package net.thumbtack.school.auction.database;
import net.thumbtack.school.auction.exception.ServerErrorCode;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.model.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DataBase {

    private static DataBase ourInstance = new DataBase();
    private Map<Integer, User> userByID = new HashMap<>();
    private Map<String, User> userByLogin = new HashMap<>();
    private BidiMap<UUID, User> userByToken = new DualHashBidiMap<>();

    private MultiValuedMap<Seller, Lot> lotsBySeller = new HashSetValuedHashMap<>();

    private MultiValuedMap<Integer, Lot> lotsByCategoryId = new HashSetValuedHashMap<>();
    private MultiValuedMap<Lot, Category> lotWithCategories = new HashSetValuedHashMap<>();

    private MultiValuedMap<Category, Lot> categoriesLots = new HashSetValuedHashMap<>();
    private Map<Integer, Lot> lotById = new HashMap<>();

    private Map<Integer, Price> priceById = new HashMap<>();

    private Map<Integer, Category> categoryById = new HashMap<>();

    private int nextUserId = 1;
    private int nextLotId = 1;
    private int nextCategoryId = 1;


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

    public User getByToken(UUID uuid) {
        return userByToken.get(uuid);
    }

    public void addLot(Lot lot) {
        lotsBySeller.put(lot.getSeller(), lot);
        lot.setId(nextLotId++);
        lotById.put(lot.getId(), lot);
    }
    
    public void addCategoryToLot(int idLot, int idCategory) throws ServerException {
        Lot lot = lotById.get(idLot);
        if (lot == null) {
            throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
        }
        Category category = categoryById.get(idCategory);
        if (category == null || !categoryById.containsKey(idCategory)){
            throw new ServerException(ServerErrorCode.CATEGORY_NOT_FOUND);
        }
        if(lot.getCategories().contains(category)){
            throw new ServerException(ServerErrorCode.CATEGORY_ALREADY_HERE);
        }
        List<Category> categoryList = lot.getCategories();
        categoryList.add(category);
        lot.setCategories(categoryList);
        lotWithCategories.put(lot, category);
        lotsByCategoryId.put(idCategory, lot);
        categoriesLots.put(category, lot);
    }

    public void deleteCategoryFromLot(int idLot, int idCategory) throws ServerException {
        Lot lot = lotById.get(idLot);
        if (lot == null) {
            throw new ServerException(ServerErrorCode.LOT_NOT_FOUND);
        }
        Category category = categoryById.get(idCategory);
        if (category == null || !categoryById.containsKey(idCategory)){
            throw new ServerException(ServerErrorCode.CATEGORY_NOT_FOUND);
        }
        List<Category> categoryList = lot.getCategories();
        if(!categoryList.contains(category)){
            throw new ServerException(ServerErrorCode.CATEGORY_NOT_FOUND);
        }
        categoryList.remove(category);
        lot.setCategories(categoryList);
        lotWithCategories.remove(category);
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

    public Collection<Lot> getListByListCategoryUnion(List<Integer> idCategories) {

        List<Category> categories = new ArrayList<>();
        Collection<Lot> lotCollection = new HashSet<>();
        for(int item: idCategories) {
            for(Map.Entry<Integer, Category> entry: categoryById.entrySet()){
                if (entry.getKey() == item){
                    categories.add(entry.getValue());
                }
            }
        }

        for(int i = 0; i < categories.size()-1; i++){
            lotCollection.addAll(CollectionUtils.union
                    (categoriesLots.get(categories.get(i)), categoriesLots.get(categories.get(i+1))));
        }
        return lotCollection;
    }

    public Collection<Lot> getListByListCategoryIntersection(List<Integer> idCategories) {

        List<Category> categories = new ArrayList<>();
        Collection<Lot> lotCollection = new HashSet<>();
        for (int item : idCategories) {
            for (Map.Entry<Integer, Category> entry : categoryById.entrySet()) {
                if (entry.getKey() == item) {
                    categories.add(entry.getValue());
                }
            }
        }

        for(int i = 0; i < categories.size()-1; i++){
            lotCollection.addAll(CollectionUtils.intersection
                    (categoriesLots.get(categories.get(i)), categoriesLots.get(categories.get(i+1))));
        }
        return lotCollection;
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
        nextUserId = 1;
        nextLotId = 1;
        nextCategoryId = 1;
    }

    public static synchronized DataBase getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataBase();
        }
        return ourInstance;
    }

    private DataBase() {
        try {
            loadCategories(Files.readAllLines
                    (Path.of("categories.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categoryById = Collections.unmodifiableMap(categoryById);
    }

    private void loadCategories(List<String> categories) {
        for(String category: categories){
            categoryById.put(nextCategoryId, new Category(nextCategoryId, category));
            nextCategoryId++;
        }
    }
}

