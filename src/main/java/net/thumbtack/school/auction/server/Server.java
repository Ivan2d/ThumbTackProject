package net.thumbtack.school.auction.server;
import net.thumbtack.school.auction.dto.request.AddCategoryRequest;
import net.thumbtack.school.auction.service.BuyerService;
import net.thumbtack.school.auction.service.DebugService;
import net.thumbtack.school.auction.service.SellerService;
import net.thumbtack.school.auction.service.UserService;
public class Server {

    private BuyerService buyerService = new BuyerService();
    private SellerService sellerService = new SellerService();
    private UserService userService = new UserService();
    private DebugService debugService = new DebugService();

    public ServerResponse registerBuyer(String requestJsonString){
        return userService.registerBuyer(requestJsonString);
    }

    public ServerResponse registerSeller(String requestJsonString){
        return userService.registerSeller(requestJsonString);
    }

    public ServerResponse loginUserBuyer (String requestJsonString) {
        return buyerService.loginBuyer(requestJsonString);
    }

    public ServerResponse loginUserSeller (String requestJsonString) {
        return sellerService.loginSeller(requestJsonString);
    }

    public ServerResponse logoutUserBuyer(String token){
        return buyerService.logoutBuyer(token);
    }

    public ServerResponse logoutUserSeller(String token){
        return sellerService.logoutSeller(token);
    }

    public ServerResponse addLot(String token, String requestJsonString) {
        return sellerService.addLotOnAuction(token, requestJsonString);
    }

    public ServerResponse deleteLot(String token, String requestJsonString) {
        return sellerService.deleteLotOnAuction(token, requestJsonString);
    }

    public ServerResponse addCategoryToLot(String token, String requestJsonString) {
        return sellerService.addCategoryToLot(token, requestJsonString);
    }

    public ServerResponse deleteCategoryFromLot(String token, String requestJsonString) {
        return sellerService.deleteCategoryFromLot(token, requestJsonString);
    }

    public ServerResponse addPrice(String token, String requestJsonString) {
        return buyerService.addPrice(token, requestJsonString);
    }

    public ServerResponse getUserDtoResponse(String token) {
        return userService.getUserByTokenResponse(token);
    }

    public ServerResponse getInfoLot(String token, String requestJsonString) {
        return buyerService.getInfoLot(token, requestJsonString);
    }

    public void clear() {
        debugService.clear();
    }

    public ServerResponse addCategory(String token, String requestJsonString) {
        return sellerService.addCategory(token, requestJsonString);
    }
}

