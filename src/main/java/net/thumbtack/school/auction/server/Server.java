package net.thumbtack.school.auction.server;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.service.BuyerService;
import net.thumbtack.school.auction.service.SellerService;
import net.thumbtack.school.auction.service.UserService;

public class Server {

    private BuyerService buyerService = new BuyerService();
    private SellerService sellerService = new SellerService();
    private UserService userService = new UserService();

    public ServerResponse registerBuyer (String requestJsonString) throws UserException {
        return buyerService.registerUser(requestJsonString);
    }
    public ServerResponse registerSeller (String requestJsonString) throws UserException {
        return sellerService.registerUser(requestJsonString);
    }

    public ServerResponse loginUser (String requestJsonString) throws UserException {
        return userService.login(requestJsonString);
    }

    public ServerResponse logoutUser (String requestJsonString) throws UserException {
        return userService.logout(requestJsonString);
    }
}

