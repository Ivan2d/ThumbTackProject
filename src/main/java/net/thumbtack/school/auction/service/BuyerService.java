package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dao.BuyerDao;
import net.thumbtack.school.auction.daoimpl.BuyerDaoImpl;
import net.thumbtack.school.auction.dto.request.LoginBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutSellerDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterBuyerDtoRequest;
import net.thumbtack.school.auction.dto.response.LoginBuyerDtoResponce;
import net.thumbtack.school.auction.dto.response.LogoutBuyerDtoResponce;
import net.thumbtack.school.auction.dto.response.RegisterSellerDtoResponce;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Buyer;
import org.apache.commons.lang3.StringUtils;

public class BuyerService {
    private static BuyerDao buyerDao = new BuyerDaoImpl();
    private static Gson gson = new Gson();
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;

    public ServerResponse registerUser(String requestJsonString) throws UserException, JsonSyntaxException
    {
        try {
            RegisterBuyerDtoRequest dtoRequest = gson.fromJson(requestJsonString, RegisterBuyerDtoRequest.class);
            checkRequest(dtoRequest);
            Buyer buyer = new Buyer(dtoRequest.getFirstName(), dtoRequest.getLastName(), dtoRequest.getLogin(), dtoRequest.getPassword());
            RegisterSellerDtoResponce registerUserDtoResponse = new RegisterSellerDtoResponce(buyerDao.insert(buyer));
        } catch (UserException e)
        {
            return new ServerResponse(400, gson.toJson(e));
        }

        return new ServerResponse(200, gson.toJson(""));
    }

    public void checkRequest(RegisterBuyerDtoRequest request) throws UserException {
        if(request.getFirstName() == null || StringUtils.isEmpty(request.getFirstName()))
            throw new UserException(UserErrorCode.EMPTY_FIRST_NAME);
        if(request.getLastName() == null || StringUtils.isEmpty(request.getLastName()))
            throw new UserException(UserErrorCode.EMPTY_LAST_NAME);
        if(request.getLogin() == null || StringUtils.isEmpty(request.getLogin()))
            throw new UserException(UserErrorCode.EMPTY_LOGIN);
        if (request.getLogin().length() < MIN_LOGIN_LEN)
            throw new UserException(UserErrorCode.SHORT_LOGIN);
        if(request.getPassword() == null || StringUtils.isEmpty(request.getPassword()))
            throw new UserException(UserErrorCode.EMPTY_PASSWORD);
        if (request.getPassword().length() < MIN_PASSWORD_LEN)
            throw new UserException(UserErrorCode.SHORT_PASSWORD);
    }

    public static ServerResponse loginBuyer (String requestJsonString) throws UserException {
        ServerResponse response = null;
        Gson gson = new Gson();
        LoginBuyerDtoRequest ludr = gson.fromJson(requestJsonString,LoginBuyerDtoRequest.class);
        try {
            LoginBuyerDtoResponce loginUserDtoResponce = new LoginBuyerDtoResponce(buyerDao.loginUser(ludr));
            response = new ServerResponse(200, gson.toJson(loginUserDtoResponce));
        } catch (UserException e) {
            return new ServerResponse(400, gson.toJson(e));
        }
        return response;
    }

    public static ServerResponse logoutBuyer (String requestJsonString) throws UserException {
        ServerResponse response = null;
        Gson gson = new Gson();
        LogoutBuyerDtoRequest lbdr = gson.fromJson(requestJsonString, LogoutBuyerDtoRequest.class);
        try{
            LogoutBuyerDtoResponce logoutBuyerDtoRequest = new LogoutBuyerDtoResponce(buyerDao.logoutUser(lbdr));
            response = logoutBuyerDtoRequest.getResponce();
        } catch (UserException e) {
            return new ServerResponse(400, gson.toJson(e));
        }
        return response;
    }

}
