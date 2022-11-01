import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.thumbtack.school.auction.dto.request.GetUserByTokenDtoRequest;
import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
import net.thumbtack.school.auction.model.User;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.server.Server;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestAuction {
    private Gson gson = new Gson();

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
//        Seller seller = new Seller("Никита", "Асаевич", "Nicoolenko", "firetree");
//    //    ServerResponse serverResponse0 = server.logoutSeller(gson.toJson(seller));
//    //    assertEquals(serverResponse0.getResponseCode(), 400);
//        //assertEquals(serverResponse0.getResponseData(), gson.toJson(new ErrorDtoResponse(new UserException(UserErrorCode.TOKEN_NOT_FOUND))));
//        server.registerSeller(gson.toJson(seller));
//        UUID uuid = base.getToken(seller.getLogin());
//        server.loginUser(gson.toJson(seller));
//        ServerResponse serverResponse1 = server.logoutUser(gson.toJson(new LogoutDtoRequest(uuid)));
//        assertEquals(serverResponse1.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
//        assertEquals(serverResponse1.getResponseCode(), 200);
//
//    }
    @SneakyThrows
    @Test
    public void testRegisterUser() {
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest("Никитаа", "Асаевичъ", "Nicoolenkosd", "firetreesd");

        ServerResponse serverResponseRegister = server.registerSeller(gson.toJson(dtoRequest));
        assertEquals(serverResponseRegister.getResponseCode(), 200);
        EmptySuccessDtoResponse emptySuccessDtoResponse = gson.fromJson(serverResponseRegister.getResponseData(), EmptySuccessDtoResponse.class);
        assertEquals(emptySuccessDtoResponse, new EmptySuccessDtoResponse());

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(dtoRequest.getLogin(), dtoRequest.getPassword());
        ServerResponse serverResponseLogin = server.loginUser(gson.toJson(loginDtoRequest));
        assertEquals(serverResponseLogin.getResponseCode(), 200);
        LoginDtoResponse loginDtoResponse = gson.fromJson(serverResponseLogin.getResponseData(), LoginDtoResponse.class);
        assertNotNull(loginDtoResponse.getToken());

        GetUserByTokenDtoRequest getUserByTokenDtoRequest = new GetUserByTokenDtoRequest(loginDtoResponse.getToken());
        User user = server.getUserByToken(gson.toJson(getUserByTokenDtoRequest));
        assertEquals(user.getFirstname(), dtoRequest.getFirstName());
        assertEquals(user.getLastname(), dtoRequest.getLastName());
        assertEquals(user.getLogin(), dtoRequest.getLogin());
        assertEquals(user.getPassword(), dtoRequest.getPassword());

        LogoutDtoRequest logoutDtoRequest = new LogoutDtoRequest(loginDtoResponse.getToken());
        ServerResponse serverResponseLogout = server.logoutUser(gson.toJson(logoutDtoRequest));
        assertEquals(serverResponseLogout.getResponseCode(), 200);
    }
}


