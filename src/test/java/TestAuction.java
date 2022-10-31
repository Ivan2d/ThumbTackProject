//import com.google.gson.Gson;
//import net.thumbtack.school.auction.server.ServerResponse;
//import net.thumbtack.school.auction.database.DataBase;
//import net.thumbtack.school.auction.dto.request.LogoutSellerDtoRequest;
//import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
//import net.thumbtack.school.auction.exception.UserException;
//import net.thumbtack.school.auction.model.Seller;
//import net.thumbtack.school.auction.server.Server;
//import org.junit.jupiter.api.Test;
//import java.util.UUID;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//
//public class TestAuction {
//    private Gson gson = new Gson();
//    private EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();
//
//
//    @Test
//    public void testLoginUser() throws UserException {
//        Server server = new Server();
//        Seller seller = new Seller("Никита", "Асаевич", "Nicoolenko", "firetreef");
//        ServerResponse serverResponse0 = server.loginUser(gson.toJson(seller));
//        assertEquals(serverResponse0.getResponseCode(), 400);
//        server.registerSeller(gson.toJson(seller));
//        ServerResponse serverResponse2 = server.loginUser(gson.toJson(seller));
//        assertEquals(serverResponse2.getResponseData(), "v");
//        assertEquals(serverResponse2.getResponseCode(), 200);
//    }
//    @Test
//    public void testLogoutUser() throws UserException {
//        Server server = new Server();
//        DataBase base = DataBase.getInstance();
//        Seller seller = new Seller("Никита", "Асаевич", "Nicoolenko", "firetree");
//    //    ServerResponse serverResponse0 = server.logoutSeller(gson.toJson(seller));
//    //    assertEquals(serverResponse0.getResponseCode(), 400);
//        //assertEquals(serverResponse0.getResponseData(), gson.toJson(new ErrorDtoResponse(new UserException(UserErrorCode.TOKEN_NOT_FOUND))));
//        server.registerSeller(gson.toJson(seller));
//        UUID uuid = base.getToken(seller.getLogin());
//        server.loginUser(gson.toJson(seller));
//        ServerResponse serverResponse1 = server.logoutUser(gson.toJson(new LogoutSellerDtoRequest(uuid)));
//        assertEquals(serverResponse1.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
//        assertEquals(serverResponse1.getResponseCode(), 200);
//
//    }
//
//    @Test
//    public void testRegisterUser() throws UserException {
//        Server server = new Server();
//        Seller seller = new Seller("Никитаа", "Асаевичъ", "Nicoolenkosd", "firetreesd");
//        String jsonRequest = "{\"error\":\"This json is wrong\"}";
//        String result = "{\"error\":\"Empty first name\"}";
//
//        ServerResponse serverResponse1 = server.registerSeller(gson.toJson(seller));
//        assertEquals(serverResponse1.getResponseData(), gson.toJson(emptySuccessDtoResponse));
//        assertEquals(serverResponse1.getResponseCode(), 200);
//
//        ServerResponse serverResponse2 = server.registerSeller(jsonRequest);
//        assertEquals(serverResponse2.getResponseCode(), 400);
//        assertEquals(serverResponse2.getResponseData(), result);
//
//        ServerResponse serverResponse3 = server.loginUser(gson.toJson(seller));
//        assertEquals(serverResponse3.getResponseCode(), 200);
//        assertTrue(serverResponse3.getResponseData() != null);
//    }
//}
//
//
