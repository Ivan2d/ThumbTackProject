package net.thumbtack.school.auction.server;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.service.BuyerService;
import net.thumbtack.school.auction.service.SellerService;

public class Server {

    private BuyerService buyerService = new BuyerService();
    private SellerService sellerService =  new SellerService();

    public ServerResponse registerBuyer (String requestJsonString) throws UserException {
        return buyerService.registerUser(requestJsonString);
    }

    public ServerResponse registerSeller (String requestJsonString) throws UserException {
        return sellerService.registerUser(requestJsonString);
    }

    public ServerResponse loginBuyer (String requestJsonString) throws UserException {
        return buyerService.loginBuyer(requestJsonString);
    }

    public ServerResponse loginSeller (String requestJsonString) throws UserException {
        return sellerService.loginSeller(requestJsonString);
    }

    public ServerResponse logoutBuyer (String requestJsonString) throws UserException {
       return buyerService.logoutBuyer(requestJsonString);
    }

    public ServerResponse logoutSeller (String requestJsonString) throws UserException {
       return sellerService.logoutSeller(requestJsonString);
    }
}

