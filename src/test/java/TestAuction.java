import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.thumbtack.school.auction.dto.request.GetUserByTokenDtoRequest;
import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.dto.request.LogoutDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
// REVU тест это клиент, он внутренности сервера не видит
// поэтому лассы модели тут использоват нельзя
// а также DAO, сервисы и БД
// только Server, ServerResponse и DTO
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

        GetUserByTokenDtoRequest getUserByTokenDtoRequest = new GetUserByTokenDtoRequest(loginDtoResponse.getToken());
        User user = server.getUserByToken(gson.toJson(getUserByTokenDtoRequest));
        assertEquals(user.getLogin(), dtoRequest.getLogin());
        assertEquals(user.getPassword(), dtoRequest.getPassword());

        LogoutDtoRequest logoutDtoRequest = new LogoutDtoRequest(loginDtoResponse.getToken());
        ServerResponse serverResponseLogout = server.logoutUser(gson.toJson(logoutDtoRequest));
        assertEquals(serverResponseLogout.getResponseCode(), 200);
    }
}


