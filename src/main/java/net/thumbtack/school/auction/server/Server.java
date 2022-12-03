package net.thumbtack.school.auction.server;
// REVU сервер не знает классов модели и исключений
// уберите эти 2 import
import net.thumbtack.school.auction.dto.response.UserDtoResponse;
import net.thumbtack.school.auction.exception.ServerException;
import net.thumbtack.school.auction.service.BuyerService;
import net.thumbtack.school.auction.service.SellerService;
import net.thumbtack.school.auction.service.UserService;

public class Server {

    private BuyerService buyerService = new BuyerService();
    private SellerService sellerService = new SellerService();
    private UserService userService = new UserService();

    public ServerResponse registerBuyer (String requestJsonString) throws ServerException {
        return buyerService.registerUser(requestJsonString);
    }
    public ServerResponse registerSeller (String requestJsonString) throws ServerException {
        return sellerService.registerUser(requestJsonString);
    }

    public ServerResponse loginUser (String requestJsonString) throws ServerException {
        return userService.login(requestJsonString);
    }

    public ServerResponse logoutUser (String requestJsonString) throws ServerException {
        return userService.logout(requestJsonString);
    }

    public UserDtoResponse getUserByToken (String requestJsonString) throws ServerException {
        return userService.getUserByToken(requestJsonString);
    }
}

