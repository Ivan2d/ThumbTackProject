package net.thumbtack.school.auction.server;

import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.service.BuyerService;
import net.thumbtack.school.auction.service.SellerService;

public class Server {

    private BuyerService buyerService = new BuyerService();
    private SellerService sellerService =  new SellerService();

    public ServerResponse registerBuyer (String requestJsonString) throws UserException {
        ServerResponse result = null;
        result = buyerService.registerUser(requestJsonString);
        return result;
    }

    public ServerResponse registerSeller (String requestJsonString) throws UserException {
        ServerResponse result = null;
        result = sellerService.registerUser(requestJsonString);
        return result;
    }

    public ServerResponse loginBuyer (String requestJsonString) throws UserException {
        ServerResponse result = null;
        result = BuyerService.loginBuyer(requestJsonString);
        return result;
    }

    public ServerResponse loginSeller (String requestJsonString) throws UserException {
        ServerResponse result = null;
        result = SellerService.loginSeller(requestJsonString);
        return result;
    }

    public ServerResponse logoutBuyer (String requestJsonString) throws UserException {
        ServerResponse result = null;
        result = BuyerService.logoutBuyer(requestJsonString);
        return result;
    }

    public ServerResponse logoutSeller (String requestJsonString) throws UserException {
        ServerResponse result = null;
        result = SellerService.logoutSeller(requestJsonString);
        return result;
    }
}
