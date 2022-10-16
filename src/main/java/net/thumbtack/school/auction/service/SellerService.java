package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.daoimpl.SellerDaoImpl;
import net.thumbtack.school.auction.dto.RegisterSellerDtoRequest;
import net.thumbtack.school.auction.dto.RegisterSellerDtoResponce;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;

public class SellerService {

    protected static SellerDao sellerDao = new SellerDaoImpl();
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;
    public static ServerResponse registerUser (String requestJsonString) throws UserException {
        String gsonText = null;
        Seller seller = null;
        Gson gson = new Gson();
        RegisterSellerDtoRequest rudr = gson.fromJson(requestJsonString, RegisterSellerDtoRequest.class);
        Gson gson2 = new Gson();
        try {
            checkFirstName (rudr.getFirstName());
        } catch (UserException e) {
            return new ServerResponse(400, gson2.toJson(e));
        }
        try {
            checkLastName(rudr.getLastName());
        } catch (UserException e) {
            return new ServerResponse(400, gson2.toJson(e));
        }
        try {
            checkLogin(rudr.getLogin());
        } catch (UserException e) {
            return new ServerResponse(400, gson2.toJson(e));
        }
        try {
            checkPassword(rudr.getPassword());
        } catch (UserException e) {
            return new ServerResponse(400, gson2.toJson(e));
        }
        seller = new Seller(rudr.getFirstName(), rudr.getLastName(), rudr.getLogin(), rudr.getPassword());
        try {
            RegisterSellerDtoResponce registerUserDtoResponce = new RegisterSellerDtoResponce(sellerDao.insert(seller));
        } catch (UserException e) {
            return new ServerResponse(400, gson2.toJson(e));
        }

        return new ServerResponse(200, gson2.toJson(""));
    }

    public static void checkFirstName(String firstName) throws UserException {
        if(firstName == null || firstName.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_FIRST_NAME);
    }

    public static void checkLastName(String lastName) throws UserException {
        if(lastName == null || lastName.replaceAll(" ", "").length() == 0 )
            throw new UserException(UserErrorCode.EMPTY_LAST_NAME);
    }

    public static void checkLogin(String login) throws UserException {
        if(login == null || login.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_LOGIN);
        if (login.length() < MIN_LOGIN_LEN)
            throw new UserException(UserErrorCode.SHORT_LOGIN);
    }

    public static void checkPassword(String password) throws UserException {
        if(password == null || password.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_PASSWORD);
        if (password.length() < MIN_PASSWORD_LEN)
            throw new UserException(UserErrorCode.SHORT_PASSWORD);
    }
}
