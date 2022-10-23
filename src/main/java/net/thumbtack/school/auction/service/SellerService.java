package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.daoimpl.SellerDaoImpl;
import net.thumbtack.school.auction.dto.*;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;

public class SellerService {

    private static SellerDao sellerDao = new SellerDaoImpl();
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;
    public static ServerResponse registerUser (String requestJsonString) throws UserException {
        String gsonText = null;
        Seller seller = null;
        Gson gson = new Gson();
        RegisterSellerDtoRequest dtoRequest = gson.fromJson(requestJsonString, RegisterSellerDtoRequest.class);
        Gson gson2 = new Gson();
        try {
            checkFirstName (dtoRequest.getFirstName());
        } catch (UserException e) {
            return new ServerResponse(400, gson2.toJson(e));
        }
        try {
            checkLastName(dtoRequest.getLastName());
        } catch (UserException e) {
            return new ServerResponse(400, gson2.toJson(e));
        }
        try {
            checkLogin(dtoRequest.getLogin());
        } catch (UserException e) {
            return new ServerResponse(400, gson2.toJson(e));
        }
        try {
            checkPassword(dtoRequest.getPassword());
        } catch (UserException e) {
            return new ServerResponse(400, gson2.toJson(e));
        }
        seller = new Seller(dtoRequest.getFirstName(), dtoRequest.getLastName(), dtoRequest.getLogin(), dtoRequest.getPassword());
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

    public static String loginSeller (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        LoginSellerDtoRequest ludr = gson.fromJson(requestJsonString,LoginSellerDtoRequest.class);
        try {
            LoginBuyerDtoResponce loginUserDtoResponce = new LoginBuyerDtoResponce(sellerDao.loginUser(ludr));
            gsonText = gson.toJson(loginUserDtoResponce);
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }


}
