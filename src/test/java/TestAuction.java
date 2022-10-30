import com.google.gson.Gson;
import net.thumbtack.school.auction.ServerResponse;
import net.thumbtack.school.auction.database.DataBase;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.exception.UserException;
import net.thumbtack.school.auction.model.Seller;
import net.thumbtack.school.auction.server.Server;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

public class TestAuction {
    private Gson gson = new Gson();
    private EmptySuccessDtoResponse emptySuccessDtoResponse = new EmptySuccessDtoResponse();

    @Test
    public void testRegisterUser() throws UserException {
        DataBase dataBase = DataBase.getInstance();
        Server server = new Server();
        Seller seller = new Seller("Никита", "Асаевич", "Nicoolenko", "firetree");
        String jsonRequest = "d";
        String result = "{\"error\":\"This json is wrong\"}";

        ServerResponse serverResponse1 = server.registerSeller(gson.toJson(seller));
        assertEquals(dataBase.getUsers().size(), 1);

        assertEquals(serverResponse1.getResponseCode(), 200);
        assertEquals(serverResponse1.getResponseData(), gson.toJson(emptySuccessDtoResponse));

        assertEquals(dataBase.getUsers().size(), 1);

        ServerResponse serverResponse2 = server.registerSeller(jsonRequest);
        assertEquals(serverResponse2.getResponseCode(), 400);
        assertEquals(serverResponse2.getResponseData(), result);

        assertEquals(dataBase.getUsers().size(), 1);
    }

    @Test
    public void testLoginUser() throws UserException{
        Server server = new Server();
        DataBase dataBase = DataBase.getInstance();
        Seller seller = new Seller("Никита", "Асаевич", "Nicoolenko", "firetree");
        ServerResponse serverResponse1 = server.registerSeller(gson.toJson(seller));
        assertEquals(dataBase.getUserByToken().size(), 1);
        assertEquals(dataBase.getUsers().size(), 1);
        ServerResponse serverResponse = server.loginSeller(gson.toJson(seller));
        assertEquals(serverResponse.getResponseCode(), 200);
    }

}
