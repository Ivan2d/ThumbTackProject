import com.google.gson.Gson;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.server.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestAuction {
    private final Gson gson = new Gson();

    @Test
    public void testRegisterUser() {
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosd",
                "firetreesd"
        );

        ServerResponse serverResponseRegister = server.registerSeller(gson.toJson(dtoRequest));
        Assertions.assertEquals(serverResponseRegister.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
        Assertions.assertEquals(serverResponseRegister.getResponseCode(), 200);
    }

    @Test
    public void testRegisterUserWithNullOrEmptyName(){
        Server server = new Server();
        RegisterDtoRequest nullFirstnameDtoRequest = new RegisterDtoRequest(
                null,
                "s",
                "ssssssssss",
                "12134321"
        );

        RegisterDtoRequest emptyFirstnameDtoRequest = new RegisterDtoRequest(
                "",
                "s",
                "sssssssss",
                "1232321312"
        );

        RegisterDtoRequest nullLastnameDtoRequest = new RegisterDtoRequest(
                "sdsd",
                null,
                "ssssssssss",
                "12134321"
        );

        RegisterDtoRequest emptyLastnameDtoRequest = new RegisterDtoRequest(
                "sdsd",
                "",
                "sssssssss",
                "1232321312"
        );

        ServerResponse serverNullNameResponse = server.registerSeller(gson.toJson(nullFirstnameDtoRequest));
        Assertions.assertEquals(serverNullNameResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverNullNameResponse.getResponseData(), "Empty first name");

        ServerResponse serverEmptyNameResponse = server.registerSeller(gson.toJson(emptyFirstnameDtoRequest));
        Assertions.assertEquals(serverEmptyNameResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverEmptyNameResponse.getResponseData(), "Empty first name");

        ServerResponse serverNullLastnameResponse = server.registerSeller(gson.toJson(nullLastnameDtoRequest));
        Assertions.assertEquals(serverNullLastnameResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverNullLastnameResponse.getResponseData(), "Empty last name");

        ServerResponse serverEmptyLastnameResponse = server.registerSeller(gson.toJson(emptyLastnameDtoRequest));
        Assertions.assertEquals(serverEmptyLastnameResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverEmptyLastnameResponse.getResponseData(), "Empty last name");
    }

    @Test
    public void testRegisterWithProblemLoginOrPassword(){
        Server server = new Server();
        RegisterDtoRequest nullLoginRequest = new RegisterDtoRequest(
                "sdffdg",
                "rgrgrd",
                null,
                "firetreesd"
        );

        RegisterDtoRequest emptyLoginRequest = new RegisterDtoRequest(
                "jrofjrfijo",
                "Асаевичъ",
                "",
                "eojfejofoj"
        );

        RegisterDtoRequest nullPasswordRequest = new RegisterDtoRequest(
                "depdpkekpd",
                "Аefwefewf",
                "Nicoolenkosdds",
                null
        );

        RegisterDtoRequest emptyPasswordRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosdsdsdsds",
                ""
        );

        ServerResponse nullLoginResponse = server.registerSeller(gson.toJson(nullLoginRequest));
        Assertions.assertEquals(nullLoginResponse.getResponseCode(), 400);
        Assertions.assertEquals(nullLoginResponse.getResponseData(), "Empty login");

        ServerResponse emptyLoginResponse = server.registerSeller(gson.toJson(emptyLoginRequest));
        Assertions.assertEquals(emptyLoginResponse.getResponseCode(), 400);
        Assertions.assertEquals(emptyLoginResponse.getResponseData(), "Empty login");

        ServerResponse nullPasswordResponse = server.registerSeller(gson.toJson(nullPasswordRequest));
        Assertions.assertEquals(nullPasswordResponse.getResponseCode(), 400);
        Assertions.assertEquals(nullPasswordResponse.getResponseData(), "Empty password");

        ServerResponse emptyPasswordResponse = server.registerSeller(gson.toJson(emptyPasswordRequest));
        Assertions.assertEquals(emptyPasswordResponse.getResponseCode(), 400);
        Assertions.assertEquals(emptyPasswordResponse.getResponseData(), "Empty password");

    }


    @Test
    public void testLoginUser(){
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosddsadsda",
                "firetreesd"
        );
        server.registerSeller(gson.toJson(dtoRequest));

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(
                "Nicoolenkosddsadsda", "firetreesd");

        ServerResponse serverResponseLogin = server.loginUser(gson.toJson(loginDtoRequest));
        Assertions.assertEquals(serverResponseLogin.getResponseCode(), 200);

        LoginDtoResponse loginDtoResponse = gson.fromJson(serverResponseLogin.getResponseData(), LoginDtoResponse.class);
        assertNotNull(loginDtoResponse.getToken());
    }

    @Test
    public void testLoginWithoutRegister(){
        //logout невозможен из-за того, что мы не сможем получить токен
        //пока не зарегистрируемся и не залогинимся

        Server server = new Server();

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(
                "Nicoolenkosdasdsadsadadsasd", "firetreesd");

        ServerResponse withoutRegisterResponse = server.loginUser(gson.toJson(loginDtoRequest));
        Assertions.assertEquals(withoutRegisterResponse.getResponseCode(), 400);
        Assertions.assertEquals(withoutRegisterResponse.getResponseData(), "This login or password is wrong");
    }

    @Test
    public void testLoginAndLogoutUser(){
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "ooewpwqsss",
                "firetreesd"
        );
        server.registerSeller(gson.toJson(dtoRequest));

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest
                ("ooewpwqsss", "firetreesd");

        ServerResponse RegisterResponse = server.loginUser(gson.toJson(loginDtoRequest));
        LoginDtoResponse loginDtoResponse = gson.fromJson
                (RegisterResponse.getResponseData(), LoginDtoResponse.class);

        ServerResponse logoutUserResponse = server.logoutUser(loginDtoResponse.getToken());
        Assertions.assertEquals(logoutUserResponse.getResponseCode(), 200);
        Assertions.assertEquals(logoutUserResponse.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
    }

    @Test
    public void testBuyerAddLotsLikeSeller() {
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosdasdasdsadsadsadadsa",
                "firetreesd"
        );
        server.registerBuyer(gson.toJson(dtoRequest));
        ServerResponse response = server.loginUser(gson.toJson(new LoginDtoRequest
                (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (response.getResponseData(), LoginDtoResponse.class);

        AddLotDtoRequest lotDtoRequest = new AddLotDtoRequest
                ("Table", "good", 2000);

        ServerResponse buyerAddLotRequest = server.addLot
                (String.valueOf(loginDtoResponse.getToken()), gson.toJson(lotDtoRequest));

        Assertions.assertEquals(buyerAddLotRequest.getResponseCode(), 400);
        Assertions.assertEquals(buyerAddLotRequest.getResponseData(), "This user not seller");
    }

    @Test
    public void testSellerAddPriceLikeBuyer() {
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosd12312ewsaeqwe",
                "firetreesd"
        );
        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse response = server.loginUser(gson.toJson(new LoginDtoRequest
                (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (response.getResponseData(), LoginDtoResponse.class);

        AddPriceDtoRequest priceDtoRequest = new AddPriceDtoRequest(2000, 1);

        ServerResponse serverResponse = server.addPrice
                (String.valueOf(loginDtoResponse.getToken()), gson.toJson(priceDtoRequest));

        Assertions.assertEquals(serverResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverResponse.getResponseData(), "This user not buyer");
    }


    @Test
    public void testDeleteWithoutAddAndAddPlusDeleteLot() {
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosdasdqwfdfdfsads",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));
        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest(dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);
        String uuid = loginDtoResponse.getToken().toString();

        DeleteLotDtoRequest deleteLotWithoutAddDtoRequest = new DeleteLotDtoRequest(1);
        ServerResponse deleteWithoutAddResponse = server.deleteLot
                (uuid, gson.toJson(deleteLotWithoutAddDtoRequest));
        Assertions.assertEquals(deleteWithoutAddResponse.getResponseCode(), 400);

        AddLotDtoRequest addLotDtoRequest = new AddLotDtoRequest
                ("Table", "good", 2000);

        ServerResponse addResponse = server.addLot(uuid, gson.toJson(addLotDtoRequest));
        Assertions.assertEquals(addResponse.getResponseCode(), 200);
        Assertions.assertEquals(addResponse.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));

        DeleteLotDtoRequest deleteLotDtoRequest = new DeleteLotDtoRequest(1);

        ServerResponse deleteResponse = server.deleteLot(uuid, gson.toJson(deleteLotDtoRequest));
        Assertions.assertEquals(deleteResponse.getResponseCode(), 200);
        Assertions.assertEquals(deleteResponse.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
    }
    @Test
    public void testAddLotWithProblemData() {
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "jmishenko",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));
        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);
        String uuid = loginDtoResponse.getToken().toString();

        AddLotDtoRequest addLotEmptyNameDtoRequest = new AddLotDtoRequest
                ("", "good", 2000);

        ServerResponse addLotEmptyNameResponse = server.addLot
                (uuid, gson.toJson(addLotEmptyNameDtoRequest));

        Assertions.assertEquals(addLotEmptyNameResponse.getResponseCode(), 400);
        Assertions.assertEquals
                (addLotEmptyNameResponse.getResponseData(), "Empty first name");

        AddLotDtoRequest addLotNullNameDtoRequest = new AddLotDtoRequest
                (null, "aboba", 2000);

        ServerResponse addLotNullNameResponse = server.addLot
                (uuid, gson.toJson(addLotNullNameDtoRequest));

        Assertions.assertEquals
                (addLotNullNameResponse.getResponseCode(), 400);
        Assertions.assertEquals
                (addLotNullNameResponse.getResponseData(), "Empty first name");

        AddLotDtoRequest addLotEmptyDescriptionRequest = new AddLotDtoRequest
                ("Table", "", 2000);

        ServerResponse addLotEmptyDescriptionResponse = server.addLot
                (uuid, gson.toJson(addLotEmptyDescriptionRequest));

        Assertions.assertEquals(addLotEmptyDescriptionResponse.getResponseCode(), 400);
        Assertions.assertEquals
                (addLotEmptyDescriptionResponse.getResponseData(), "Empty description");

        AddLotDtoRequest addLotNegativeValueForSell = new AddLotDtoRequest
                ("Table", "aboba", -2000);

        ServerResponse addLotNegativeValueForSellResponse = server.addLot
                (uuid, gson.toJson(addLotNegativeValueForSell));

        Assertions.assertEquals(addLotNegativeValueForSellResponse.getResponseCode(), 400);
        Assertions.assertEquals
                (addLotNegativeValueForSellResponse.getResponseData(), "Value can't be < 0");
    }

    @Test
    public void testAddPriceNotExistLot() {
        Server server = new Server();
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "weweweqwewqe",
                "firetreesd"
        );

        server.registerBuyer(gson.toJson(dtoRequest));
        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = loginDtoResponse.getToken().toString();

        AddPriceDtoRequest priceDtoRequest = new AddPriceDtoRequest(2000, 1);

        ServerResponse addPriceServerResponse = server.addPrice(uuid, gson.toJson(priceDtoRequest));
        Assertions.assertEquals(addPriceServerResponse.getResponseData(), "This lot not found");
        Assertions.assertEquals(addPriceServerResponse.getResponseCode(), 400);

    }

//    @Test
//    public void testAddPriceExistLot() {
//        Server server = new Server();
//
//        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
//                "Никитаа",
//                "Асаевичъ",
//                "qwertyuiosspp",
//                "firetreesddas"
//        );
//
//        RegisterDtoRequest dtoRequestBuyer = new RegisterDtoRequest(
//                "Никитаа",
//                "Асаевичъ",
//                "qwertyuiopp",
//                "firetreesdpoisiodi"
//        );
//
//        server.registerSeller(gson.toJson(dtoRequest));
//        server.registerBuyer(gson.toJson(dtoRequestBuyer));
//
//        ServerResponse serverResponseSeller = server.loginUser
//                (gson.toJson(new LoginDtoRequest
//                        (dtoRequest.getLogin(), dtoRequest.getPassword())));
//
//        ServerResponse serverResponseBuyer = server.loginUser
//                (gson.toJson(new LoginDtoRequest
//                        (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));
//
//        LoginDtoResponse loginDtoResponseSeller = gson.fromJson
//                (serverResponseSeller.getResponseData(), LoginDtoResponse.class);
//
//        String sellerUuid = loginDtoResponseSeller.getToken().toString();
//
//        LoginDtoResponse loginDtoResponseBuyer = gson.fromJson
//                (serverResponseBuyer.getResponseData(), LoginDtoResponse.class);
//
//        String buyerUuid = loginDtoResponseBuyer.getToken().toString();
//
//        ServerResponse serverResponseAddLot = server.addLot(sellerUuid, gson.toJson
//                (new AddLotDtoRequest("Table", "good_quality", 2000)));
//        Assertions.assertEquals
//                (serverResponseAddLot.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
//        Assertions.assertEquals(serverResponseAddLot.getResponseCode(), 200);
//
//        ServerResponse serverResponseAddPrice = server.addPrice(buyerUuid, gson.toJson
//                (new AddPriceDtoRequest(200, 1)));
//        Assertions.assertEquals
//                (serverResponseAddPrice.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
//        Assertions.assertEquals(serverResponseAddPrice.getResponseCode(), 200);
//
//    }

}


