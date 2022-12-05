import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.thumbtack.school.auction.dto.request.AddLotDtoRequest;
import net.thumbtack.school.auction.dto.request.DeleteLotDtoRequest;
import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
import net.thumbtack.school.auction.exception.ServerException;
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
    public void testRegisterLoginLogoutUser() {
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest("Никитаа", "Асаевичъ", "Nicoolenkosd", "firetreesd");
        RegisterDtoRequest wrongDtoRequest = new RegisterDtoRequest(null, "s", "ssssssssss","12134321");

        ServerResponse serverResponseRegister = server.registerSeller(gson.toJson(dtoRequest));
        assertEquals(serverResponseRegister.getResponseCode(), 200);
        EmptySuccessDtoResponse emptySuccessDtoResponse = gson.fromJson(serverResponseRegister.getResponseData(), EmptySuccessDtoResponse.class);
        assertEquals(emptySuccessDtoResponse, new EmptySuccessDtoResponse());

        ServerResponse serverWrongResponseRegister = server.registerSeller(gson.toJson(wrongDtoRequest));
        assertEquals(serverWrongResponseRegister.getResponseCode(), 400);
        assertEquals(serverWrongResponseRegister.getResponseData(), "Empty first name");

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(dtoRequest.getLogin(), dtoRequest.getPassword());
        ServerResponse serverResponseLogin = server.loginUser(gson.toJson(loginDtoRequest));
        assertEquals(serverResponseLogin.getResponseCode(), 200);
        LoginDtoResponse loginDtoResponse = gson.fromJson(serverResponseLogin.getResponseData(), LoginDtoResponse.class);
        assertNotNull(loginDtoResponse.getToken());

        ServerResponse serverResponseLogout = server.logoutUser(loginDtoResponse.getToken());
        assertEquals(serverResponseLogout.getResponseCode(), 200);
    }

    @Test
    public void testAddAndDeleteLot() throws ServerException {
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest("Никитаа", "Асаевичъ", "Nicoolenkosd", "firetreesd");
        server.registerSeller(gson.toJson(dtoRequest));
        ServerResponse serverResponse = server.loginUser(gson.toJson(new LoginDtoRequest(dtoRequest.getLogin(), dtoRequest.getPassword())));
        LoginDtoResponse loginDtoResponse = gson.fromJson(serverResponse.getResponseData(), LoginDtoResponse.class);
        String uuid = loginDtoResponse.getToken().toString();

        DeleteLotDtoRequest deleteLotWithoutAddDtoRequest = new DeleteLotDtoRequest(1);
        ServerResponse deleteWithoutAddResponse = server.deleteLot(uuid, gson.toJson(deleteLotWithoutAddDtoRequest));
        assertEquals(deleteWithoutAddResponse.getResponseCode(), 400);

        AddLotDtoRequest addLotDtoRequest = new AddLotDtoRequest("Table", "good", 2000);
        ServerResponse addResponse = server.addLot(uuid, gson.toJson(addLotDtoRequest));
        assertEquals(addResponse.getResponseCode(), 200);
        assertEquals(addResponse.getResponseData(),  gson.toJson(new EmptySuccessDtoResponse()));

        DeleteLotDtoRequest deleteLotDtoRequest = new DeleteLotDtoRequest(1);
        ServerResponse deleteResponse = server.deleteLot(uuid, gson.toJson(deleteLotDtoRequest));
        assertEquals(deleteResponse.getResponseCode(), 200);

    }
}


