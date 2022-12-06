package net.thumbtack.school.auction.server;
import net.thumbtack.school.auction.service.BuyerService;
import net.thumbtack.school.auction.service.DebugService;
import net.thumbtack.school.auction.service.SellerService;
import net.thumbtack.school.auction.service.UserService;

import java.util.UUID;

public class Server {

    private BuyerService buyerService = new BuyerService();
    private SellerService sellerService = new SellerService();
    private UserService userService = new UserService();
    private DebugService debugService = new DebugService();

    public ServerResponse registerBuyer (String requestJsonString){
        return buyerService.registerUser(requestJsonString);
    }
    public ServerResponse registerSeller (String requestJsonString){
        return sellerService.registerUser(requestJsonString);
    }

    public ServerResponse loginUser (String requestJsonString) {
        return userService.login(requestJsonString);
    }

    public ServerResponse logoutUser (UUID token){
        return userService.logout(token);
    }

    public ServerResponse addLot(String token, String requestJsonString) {
        return sellerService.addLotOnAuction(token, requestJsonString);
    }

    public ServerResponse deleteLot(String token, String requestJsonString) {
        return sellerService.deleteLotOnAuction(token, requestJsonString);
    }

    public ServerResponse addPrice(String token, String requestJsonString) {
        return buyerService.addPrice(token, requestJsonString);
    }

    public ServerResponse getBuyerDtoResponse(String token) {
        return buyerService.getBuyerByTokenResponse(token);
    }
    public void clear() {
        debugService.clear();
    }
}

