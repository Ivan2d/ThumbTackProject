package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.mapper.LotMapperFromDto;
import net.thumbtack.school.auction.mapper.SellerMapperFromRegister;
import net.thumbtack.school.auction.model.Lot;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dao.SellerDao;
import net.thumbtack.school.auction.daoimpl.SellerDaoImpl;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.*;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
import org.apache.commons.lang3.StringUtils;
public class SellerService {

    // REVU см. REVU в BuyerService
    public ServerResponse registerUser(String requestJsonString) throws JsonSyntaxException {
        try {
            RegisterDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, RegisterDtoRequest.class);
            checkRequest(dtoRequest);
            Seller seller = SellerMapperFromRegister.MAPPER.toSeller(dtoRequest);
            sellerDao.insert(seller);
            EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(emptySuccessDtoResponse));
        }
        catch (UserException e) {
            ErrorDtoResponse errorDtoResponse = new ErrorDtoResponse(e);
            return new ServerResponse(CODE_ERROR, gson.toJson(errorDtoResponse));
        }
    }

    public ServerResponse addLotOnAuction(String requestJsonString) throws UserException {
            AddLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, AddLotDtoRequest.class);
            Lot lot = LotMapperFromDto.MAPPER.toLot(dtoRequest);
            sellerDao.addLot(lot);
            EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
            return new ServerResponse(CODE_SUCCESS, gson.toJson(emptySuccessDtoResponse));
    }

    // REVU а этот метод такой же, как и в BuyerService
    // подумайте, как сделать чтобы не писать дважды
    public void checkRequest(RegisterDtoRequest request) throws UserException {
        if (request.getFirstName() == null || StringUtils.isEmpty(request.getFirstName()))
            throw new UserException(UserErrorCode.EMPTY_FIRST_NAME);
        if (request.getLastName() == null || StringUtils.isEmpty(request.getLastName()))
            throw new UserException(UserErrorCode.EMPTY_LAST_NAME);
        if (request.getLogin() == null || StringUtils.isEmpty(request.getLogin()))
            throw new UserException(UserErrorCode.EMPTY_LOGIN);
        if (request.getLogin().length() < MIN_LOGIN_LEN)
            throw new UserException(UserErrorCode.SHORT_LOGIN);
        if (request.getPassword() == null || StringUtils.isEmpty(request.getPassword()))
            throw new UserException(UserErrorCode.EMPTY_PASSWORD);
        if (request.getPassword().length() < MIN_PASSWORD_LEN)
            throw new UserException(UserErrorCode.SHORT_PASSWORD);
    }

    private static SellerDao sellerDao = new SellerDaoImpl();
    private static final int MIN_LOGIN_LEN = 8;
    private static final int MIN_PASSWORD_LEN = 8;
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static Gson gson = new Gson();
}
