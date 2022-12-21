import com.google.gson.Gson;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.server.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestAuction {

    private Server server = new Server();
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;
    private final Gson gson = new Gson();

    @BeforeEach
    public void clearDataBase() {
        server.clear();
    }

    @Test
    public void testRegisterUser() {
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
    public void testRegisterUserWithNullOrEmptyName() {
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
    public void testRegisterWithProblemLoginOrPassword() {
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
    public void testLoginUser() {
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
    public void testLoginWithoutRegister() {
        //logout невозможен из-за того, что мы не сможем получить токен
        //пока не зарегистрируемся и не залогинимся

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest(
                "Nicoolenkosdasdsadsadadsasd", "firetreesd");

        ServerResponse withoutRegisterResponse = server.loginUser(gson.toJson(loginDtoRequest));
        Assertions.assertEquals(withoutRegisterResponse.getResponseCode(), 400);
        Assertions.assertEquals(withoutRegisterResponse.getResponseData(), "This login or password is wrong");
    }

    @Test
    public void testLoginAndLogoutUser() {
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

        ServerResponse getBuyerDtoUnexistedToken = server.getBuyerDtoResponse
                (String.valueOf(loginDtoResponse.getToken()));

        Assertions.assertEquals(getBuyerDtoUnexistedToken.getResponseCode(), 400);
        Assertions.assertEquals(getBuyerDtoUnexistedToken.getResponseData(), "This user don't exist");


    }

    @Test
    public void testBuyerAddLotsLikeSeller() {
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
    public void testAddPriceExistLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "qwertyuiosspp",
                "firetreesddas"
        );

        RegisterDtoRequest dtoRequestBuyer = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "qwertyuiopp",
                "firetreesdpoisiodi"
        );

        server.registerSeller(gson.toJson(dtoRequest));
        server.registerBuyer(gson.toJson(dtoRequestBuyer));

        ServerResponse serverResponseSeller = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        ServerResponse serverResponseBuyer = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));

        LoginDtoResponse loginDtoResponseSeller = gson.fromJson
                (serverResponseSeller.getResponseData(), LoginDtoResponse.class);

        String sellerUuid = loginDtoResponseSeller.getToken().toString();

        LoginDtoResponse loginDtoResponseBuyer = gson.fromJson
                (serverResponseBuyer.getResponseData(), LoginDtoResponse.class);

        String buyerUuid = loginDtoResponseBuyer.getToken().toString();

        ServerResponse serverResponseAddLot = server.addLot(sellerUuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));
        Assertions.assertEquals
                (serverResponseAddLot.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
        Assertions.assertEquals(serverResponseAddLot.getResponseCode(), 200);

        ServerResponse serverResponseAddPrice = server.addPrice(buyerUuid, gson.toJson
                (new AddPriceDtoRequest(200, 1)));
        Assertions.assertEquals
                (serverResponseAddPrice.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
        Assertions.assertEquals(serverResponseAddPrice.getResponseCode(), 200);

    }

    @Test
    public void testDeleteWithoutAddAndAddPlusDeleteLot() {
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

    @Test
    public void testAddCategoryToLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(1, 2);

        ServerResponse addCategoryResponse = server.addCategoryToLot
                (uuid, gson.toJson(addCategoryToLotRequest));

        Assertions.assertEquals(addCategoryResponse.getResponseCode(), 200);
        Assertions.assertEquals(addCategoryResponse.getResponseData(),
                gson.toJson(new EmptySuccessDtoResponse()));
    }

    @Test
    public void testAddCategoryToInexistedLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec2007",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(2, 1);

        ServerResponse addCategoryResponse = server.addCategoryToLot
                (uuid, gson.toJson(addCategoryToLotRequest));
        Assertions.assertEquals(addCategoryResponse.getResponseCode(), 400);
        Assertions.assertEquals(addCategoryResponse.getResponseData(), "This lot not found");
    }

    @Test
    public void testAddInexistedCategoryToLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec2007",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(1, 10);

        ServerResponse addCategoryResponse = server.addCategoryToLot
                (uuid, gson.toJson(addCategoryToLotRequest));
        Assertions.assertEquals(addCategoryResponse.getResponseCode(), 400);
        Assertions.assertEquals(addCategoryResponse.getResponseData(), "This category not found");
    }

    @Test
    public void testAddDuplicateCategoryToLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec2007",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(1, 1);

        server.addCategoryToLot(uuid, gson.toJson(addCategoryToLotRequest));

        AddCategoryToLotRequest addCategoryToLotRequestAgainRequest = new AddCategoryToLotRequest(1, 1);

        ServerResponse addCategoryAgainResponse = server.addCategoryToLot
                (uuid, gson.toJson(addCategoryToLotRequestAgainRequest));

        Assertions.assertEquals(addCategoryAgainResponse.getResponseCode(), 400);
        Assertions.assertEquals(addCategoryAgainResponse.getResponseData(), "Lot already in this category");
    }

    @Test
    public void testDeleteInexistedCategoryFromLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        DeleteCategoryFromLotRequest request = new DeleteCategoryFromLotRequest(1, 1);

        ServerResponse deleteInexistedCategoryResponse = server.deleteCategoryFromLot(uuid, gson.toJson(request));

        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseCode(), 400);
        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseData(), "This category not found");
    }

    @Test
    public void testDeleteCategoryFromInexistedLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        DeleteCategoryFromLotRequest request = new DeleteCategoryFromLotRequest(2, 1);

        ServerResponse deleteInexistedCategoryResponse = server.deleteCategoryFromLot(uuid, gson.toJson(request));

        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseCode(), 400);
        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseData(), "This lot not found");
    }

    @Test
    public void testDeleteCategoryFromLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(1, 1);
        server.addCategoryToLot(uuid, gson.toJson(addCategoryToLotRequest));

        DeleteCategoryFromLotRequest request = new DeleteCategoryFromLotRequest(1, 1);
        ServerResponse deleteInexistedCategoryResponse = server.deleteCategoryFromLot(uuid, gson.toJson(request));

        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseCode(), 200);
        Assertions.assertEquals
                (deleteInexistedCategoryResponse.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
    }

    @Test
    public void testGetInfoLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        RegisterDtoRequest dtoRequestBuyer = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078ff",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        server.registerBuyer(gson.toJson(dtoRequestBuyer));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        ServerResponse serverResponseBuyer = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        LoginDtoResponse responseBuyer = gson.fromJson
                (serverResponseBuyer.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        String uuidBuyer = responseBuyer.getToken().toString();

        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        GetLotInfoRequest getLotInfoRequest = new GetLotInfoRequest(1);
        ServerResponse getInfoResponse = server.getInfoLot(uuidBuyer, gson.toJson(getLotInfoRequest));

        Assertions.assertEquals(getInfoResponse.getResponseCode(), 200);
    }

    @Test
    public void testGetInfoInexistedLot() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        RegisterDtoRequest dtoRequestBuyer = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078ff",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        server.registerBuyer(gson.toJson(dtoRequestBuyer));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        ServerResponse serverResponseBuyer = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        LoginDtoResponse responseBuyer = gson.fromJson
                (serverResponseBuyer.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        String uuidBuyer = responseBuyer.getToken().toString();

        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        GetLotInfoRequest getLotInfoRequest = new GetLotInfoRequest(2);
        ServerResponse getInfoResponse = server.getInfoLot(uuidBuyer, gson.toJson(getLotInfoRequest));

        Assertions.assertEquals(getInfoResponse.getResponseCode(), 400);
    }

    @Test
    public void testGetListInfoLotFromCategory(){
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        RegisterDtoRequest dtoRequestBuyer = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078ff",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        server.registerBuyer(gson.toJson(dtoRequestBuyer));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        ServerResponse serverResponseBuyer = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        LoginDtoResponse responseBuyer = gson.fromJson
                (serverResponseBuyer.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        String uuidBuyer = responseBuyer.getToken().toString();

        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Stol", "norm_quality", 3000)));

        AddCategoryToLotRequest addCategoryToLotRequest = new
                AddCategoryToLotRequest(1, 2);

        Assertions.assertEquals(server.addCategoryToLot
                        (uuid, gson.toJson(addCategoryToLotRequest)).
                getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));

       server.addLot(uuid, gson.toJson
               (new AddLotDtoRequest("Computer mouse", "good_quality", 500)));

       Assertions.assertEquals(server.addCategoryToLot
                (uuid, gson.toJson( new AddCategoryToLotRequest(2, 4)))
                .getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));

        Assertions.assertEquals(server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Monitor", "good_quality", 6000)))
                .getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));


        AddCategoryToLotRequest addCategoryToLotRequestComputerNext = new
                AddCategoryToLotRequest(3, 4);
        server.addCategoryToLot(uuid, gson.toJson(addCategoryToLotRequestComputerNext));

        ServerResponse listLotsResponse = server.getInfoLotByCategory
                (uuidBuyer, gson.toJson(new GetLotsInfoByCategoryRequest(4)));

        Assertions.assertEquals(listLotsResponse.getResponseCode(), 200);
    }

    @Test
    public void testGetListInfoLotFromInexistedCategory() {
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        RegisterDtoRequest dtoRequestBuyer = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078ff",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        server.registerBuyer(gson.toJson(dtoRequestBuyer));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        ServerResponse serverResponseBuyer = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        LoginDtoResponse responseBuyer = gson.fromJson
                (serverResponseBuyer.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        String uuidBuyer = responseBuyer.getToken().toString();

        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Computer mouse", "good_quality", 500)));

        Assertions.assertEquals(server.addCategoryToLot
                        (uuid, gson.toJson( new AddCategoryToLotRequest(1, 4)))
                .getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));

        Assertions.assertEquals(server.addLot(uuid, gson.toJson
                        (new AddLotDtoRequest("Monitor", "good_quality", 6000)))
                .getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));


        AddCategoryToLotRequest addCategoryToLotRequestComputerNext = new
                AddCategoryToLotRequest(2, 4);
        server.addCategoryToLot(uuid, gson.toJson(addCategoryToLotRequestComputerNext));

        ServerResponse listLotsResponse = server.getInfoLotByCategory
                (uuidBuyer, gson.toJson(new GetLotsInfoByCategoryRequest(3)));

        Assertions.assertEquals(listLotsResponse.getResponseCode(), 200);
        Assertions.assertEquals(listLotsResponse.getResponseData(), gson.toJson(new ArrayList<>()));
    }

    @Test
    public void testGetListLotsByListCategory(){
        RegisterDtoRequest dtoRequest = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        RegisterDtoRequest dtoRequestBuyer = new RegisterDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078ff",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        server.registerBuyer(gson.toJson(dtoRequestBuyer));

        ServerResponse serverResponse = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        ServerResponse serverResponseBuyer = server.loginUser
                (gson.toJson(new LoginDtoRequest
                        (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));

        LoginDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), LoginDtoResponse.class);

        LoginDtoResponse responseBuyer = gson.fromJson
                (serverResponseBuyer.getResponseData(), LoginDtoResponse.class);

        String uuid = response.getToken().toString();
        String uuidBuyer = responseBuyer.getToken().toString();

        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Computer mouse", "good_quality", 500)));

        Assertions.assertEquals(server.addCategoryToLot
                        (uuid, gson.toJson( new AddCategoryToLotRequest(1, 2)))
                .getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));

        Assertions.assertEquals(server.addCategoryToLot
                        (uuid, gson.toJson( new AddCategoryToLotRequest(1, 4)))
                .getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));

        GetLotsInfoByListCategoryRequest listLotDtoRequest = new GetLotsInfoByListCategoryRequest(List.of(3, 4));

        Assertions.assertEquals(server.getInfoLotByListCategory(uuidBuyer, gson.toJson(listLotDtoRequest))
                .getResponseData(), gson.toJson(new ArrayList<>()));
    }
}



