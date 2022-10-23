package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.daoimpl.SellerDaoImpl;
import net.thumbtack.school.auction.dto.request.LoginSellerDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterSellerDtoRequest;
import net.thumbtack.school.auction.dto.response.LoginBuyerDtoResponce;
import net.thumbtack.school.auction.dto.response.RegisterSellerDtoResponce;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
import org.apache.commons.lang3.StringUtils;

public class SellerService {
    private static SellerDao sellerDao = new SellerDaoImpl();
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;
    private static Gson gson = new Gson();

    public ServerResponse registerUser(String requestJsonString) throws UserException, JsonSyntaxException
    {
        try {
            RegisterSellerDtoRequest dtoRequest = gson.fromJson(requestJsonString, RegisterSellerDtoRequest.class);
            checkRequest(dtoRequest);
            Seller seller = new Seller(dtoRequest.getFirstName(), dtoRequest.getLastName(), dtoRequest.getLogin(), dtoRequest.getPassword());
            RegisterSellerDtoResponce registerUserDtoResponse = new RegisterSellerDtoResponce(sellerDao.insert(seller));
        } catch (UserException e)
        {
            return new ServerResponse(400, gson.toJson(e));
        }

        return new ServerResponse(200, gson.toJson(""));
    }

    public void checkRequest(RegisterSellerDtoRequest request) throws UserException {
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

    public static ServerResponse loginSeller (String requestJsonString) throws UserException {
        ServerResponse response = null;
        Gson gson = new Gson();
        LoginSellerDtoRequest ludr = gson.fromJson(requestJsonString,LoginSellerDtoRequest.class);
        try {
            LoginBuyerDtoResponce loginUserDtoResponce = new LoginBuyerDtoResponce(sellerDao.loginUser(ludr));
            response = new ServerResponse(200, gson.toJson(loginUserDtoResponce));
        } catch (UserException e) {
            return new ServerResponse(400, gson.toJson(e));
        }
        return response;
    }


}
