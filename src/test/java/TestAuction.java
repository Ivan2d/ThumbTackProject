import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.server.Server;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestAuction {
    private Gson gson = new Gson();
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

        LogoutDtoRequest logoutDtoRequest = new LogoutDtoRequest(loginDtoResponse.getToken());
        ServerResponse serverResponseLogout = server.logoutUser(gson.toJson(logoutDtoRequest));
        assertEquals(serverResponseLogout.getResponseCode(), 200);
    }
}


