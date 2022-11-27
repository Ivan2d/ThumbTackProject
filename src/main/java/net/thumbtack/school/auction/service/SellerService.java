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
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
public class SellerService {

    private static SellerDao sellerDao = new SellerDaoImpl();
    private static final int CODE_SUCCESS = 200;
    private static final int CODE_ERROR = 400;
    private static Gson gson = new Gson();

    public ServerResponse registerUser(String requestJsonString) throws JsonSyntaxException {
        try {
            RegisterDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, RegisterDtoRequest.class);
            ServiceUtils.checkRequest(dtoRequest);
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
            return new ServerResponse(CODE_SUCCESS, gson.toJson(new EmptySuccessDtoResponse()));
    }

    public ServerResponse deleteLotOnAuction(String requestJsonString) throws UserException {
            DeleteLotDtoRequest dtoRequest = ServiceUtils.getObjectFromJson(requestJsonString, DeleteLotDtoRequest.class);
            int ID = dtoRequest.getLotID();
            sellerDao.deleteLot(ID);
            return new ServerResponse(CODE_ERROR, gson.toJson(new EmptySuccessDtoResponse()));
    }

}
