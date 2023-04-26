import com.google.gson.Gson;
import net.thumbtack.school.auction.dto.request.*;
import net.thumbtack.school.auction.dto.response.AddCategoryResponse;
import net.thumbtack.school.auction.dto.response.LoginDtoResponse;
import net.thumbtack.school.auction.dto.response.TokenDtoResponse;
import net.thumbtack.school.auction.server.ServerResponse;
import net.thumbtack.school.auction.dto.response.EmptySuccessDtoResponse;
import net.thumbtack.school.auction.server.Server;
import net.thumbtack.school.auction.utils.MyBatisUtils;
import org.apache.ibatis.ognl.Token;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestAuction {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Server server = new Server();
    private static final int SUCCESS_CODE = 200;
    private static final int ERROR_CODE = 400;
    private final Gson gson = new Gson();

    @BeforeAll
    public static void setUp() {
        MyBatisUtils.initSqlSessionFactory();
    }

    @BeforeEach
    public void clearDataBase() {
        server.clear();
    }

    @Test
    public void testRegisterUser() {
        RegisterBuyerDtoRequest dtoRequest = new RegisterBuyerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosd",
                "firetreesd"
        );

        ServerResponse serverResponseRegister = server.registerBuyer(gson.toJson(dtoRequest));
        Assertions.assertEquals(serverResponseRegister.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
        Assertions.assertEquals(serverResponseRegister.getResponseCode(), 200);
    }

    @Test
    public void testRegisterUserWithNullOrEmptyName() {
        RegisterBuyerDtoRequest nullFirstnameDtoRequest = new RegisterBuyerDtoRequest(
                null,
                "s",
                "ssssssssss",
                "12134321"
        );

        RegisterBuyerDtoRequest emptyFirstnameDtoRequest = new RegisterBuyerDtoRequest(
                "",
                "s",
                "sssssssss",
                "1232321312"
        );

        RegisterBuyerDtoRequest nullLastnameDtoRequest = new RegisterBuyerDtoRequest(
                "sdsd",
                null,
                "ssssssssss",
                "12134321"
        );

        RegisterBuyerDtoRequest emptyLastnameDtoRequest = new RegisterBuyerDtoRequest(
                "sdsd",
                "",
                "sssssssss",
                "1232321312"
        );

        ServerResponse serverNullNameResponse = server.registerBuyer(gson.toJson(nullFirstnameDtoRequest));
        Assertions.assertEquals(serverNullNameResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverNullNameResponse.getResponseData(), "Empty first name");

        ServerResponse serverEmptyNameResponse = server.registerBuyer(gson.toJson(emptyFirstnameDtoRequest));
        Assertions.assertEquals(serverEmptyNameResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverEmptyNameResponse.getResponseData(), "Empty first name");

        ServerResponse serverNullLastnameResponse = server.registerBuyer(gson.toJson(nullLastnameDtoRequest));
        Assertions.assertEquals(serverNullLastnameResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverNullLastnameResponse.getResponseData(), "Empty last name");

        ServerResponse serverEmptyLastnameResponse = server.registerBuyer(gson.toJson(emptyLastnameDtoRequest));
        Assertions.assertEquals(serverEmptyLastnameResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverEmptyLastnameResponse.getResponseData(), "Empty last name");
    }

    @Test
    public void testRegisterWithProblemLoginOrPassword() {
        RegisterBuyerDtoRequest nullLoginRequest = new RegisterBuyerDtoRequest(
                "sdffdg",
                "rgrgrd",
                null,
                "firetreesd"
        );

        RegisterBuyerDtoRequest emptyLoginRequest = new RegisterBuyerDtoRequest(
                "jrofjrfijo",
                "Асаевичъ",
                "",
                "eojfejofoj"
        );

        RegisterBuyerDtoRequest nullPasswordRequest = new RegisterBuyerDtoRequest(
                "depdpkekpd",
                "Аefwefewf",
                "Nicoolenkosdds",
                null
        );

        RegisterBuyerDtoRequest emptyPasswordRequest = new RegisterBuyerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosdsdsdsds",
                ""
        );

        ServerResponse nullLoginResponse = server.registerBuyer(gson.toJson(nullLoginRequest));
        Assertions.assertEquals(nullLoginResponse.getResponseCode(), 400);
        Assertions.assertEquals(nullLoginResponse.getResponseData(), "Empty login");

        ServerResponse emptyLoginResponse = server.registerBuyer(gson.toJson(emptyLoginRequest));
        Assertions.assertEquals(emptyLoginResponse.getResponseCode(), 400);
        Assertions.assertEquals(emptyLoginResponse.getResponseData(), "Empty login");

        ServerResponse nullPasswordResponse = server.registerBuyer(gson.toJson(nullPasswordRequest));
        Assertions.assertEquals(nullPasswordResponse.getResponseCode(), 400);
        Assertions.assertEquals(nullPasswordResponse.getResponseData(), "Empty password");

        ServerResponse emptyPasswordResponse = server.registerBuyer(gson.toJson(emptyPasswordRequest));
        Assertions.assertEquals(emptyPasswordResponse.getResponseCode(), 400);
        Assertions.assertEquals(emptyPasswordResponse.getResponseData(), "Empty password");

    }


    @Test
    public void testLoginUser() {
        RegisterBuyerDtoRequest dtoRequest = new RegisterBuyerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosddsadsda",
                "firetreesd"
        );
        LoginDtoRequest dtoRequest2 = new LoginDtoRequest(
                "Nicoolenkosddsadsda",
                "firetreesd"
        );
        server.registerBuyer(gson.toJson(dtoRequest));

        ServerResponse serverResponseLogin = server.loginUserBuyer(gson.toJson(dtoRequest2));
        Assertions.assertEquals(serverResponseLogin.getResponseCode(), 200);

        TokenDtoResponse tokenDtoResponse = gson.fromJson(serverResponseLogin.getResponseData(), TokenDtoResponse.class);
        assertNotNull(tokenDtoResponse.getUuid());
    }

    @Test
    public void testLoginWithoutRegister() {
        //logout невозможен из-за того, что мы не сможем получить токен
        //пока не зарегистрируемся и не залогинимся

        LoginDtoRequest dtoRequest = new LoginDtoRequest(
                "Nicoolenkosddsadsda",
                "firetreesdfd"
        );
        ServerResponse response = server.loginUserBuyer(gson.toJson(dtoRequest));
        Assertions.assertEquals(response.getResponseCode(), 400);
        assertNotNull(response);
    }

    @Test
    public void testLoginAndLogoutUser() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "ooewpwqsss",
                "firetreesd"
        );
        server.registerSeller(gson.toJson(dtoRequest));

        LoginDtoRequest loginDtoRequest = new LoginDtoRequest
                ("ooewpwqsss", "firetreesd");

        ServerResponse RegisterResponse = server.loginUserSeller(gson.toJson(loginDtoRequest));
        TokenDtoResponse loginDtoResponse = gson.fromJson
                (RegisterResponse.getResponseData(), TokenDtoResponse.class);

        ServerResponse logoutUserResponse = server.logoutUserBuyer(loginDtoResponse.getUuid());
        Assertions.assertEquals(logoutUserResponse.getResponseCode(), 200);
        Assertions.assertEquals(logoutUserResponse.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));


    }

    @Test
    public void testBuyerAddLotsLikeSeller() {
        RegisterBuyerDtoRequest dtoRequest = new RegisterBuyerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosdasdasdsadsadsadadsa",
                "firetreesd"
        );
        server.registerBuyer(gson.toJson(dtoRequest));
        ServerResponse response = server.loginUserBuyer(gson.toJson(new LoginDtoRequest
                (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse loginDtoResponse = gson.fromJson
                (response.getResponseData(), TokenDtoResponse.class);

        AddLotDtoRequest lotDtoRequest = new AddLotDtoRequest
                ("Table", "good", 2000);

        ServerResponse buyerAddLotRequest = server.addLot
                (loginDtoResponse.getUuid(), gson.toJson(lotDtoRequest));

        Assertions.assertEquals(buyerAddLotRequest.getResponseCode(), 400);
        Assertions.assertEquals(buyerAddLotRequest.getResponseData(), "This user not seller");
    }

    @Test
    public void testSellerAddPriceLikeBuyer() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosd12312ewsaeqwe",
                "firetreesd"
        );
        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse response = server.loginUserSeller(gson.toJson(new LoginDtoRequest
                (dtoRequest.getLogin(), dtoRequest.getPassword())));

        LoginDtoResponse loginDtoResponse = gson.fromJson
                (response.getResponseData(), LoginDtoResponse.class);

        AddPriceDtoRequest priceDtoRequest = new AddPriceDtoRequest(2000, 1);

        ServerResponse serverResponse = server.addPrice
                (String.valueOf(loginDtoResponse.getToken()), gson.toJson(priceDtoRequest));

        Assertions.assertEquals(serverResponse.getResponseCode(), 400);
        Assertions.assertEquals(serverResponse.getResponseData(), "This token not exist");
    }

    @Test
    public void testAddPriceExistLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "qwertyuiosspp",
                "firetreesddas"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponseSeller = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse loginDtoResponseSeller = gson.fromJson
                (serverResponseSeller.getResponseData(), TokenDtoResponse.class);

        String sellerUuid = loginDtoResponseSeller.getUuid();

        ServerResponse serverResponseAddLot = server.addLot(sellerUuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        Assertions.assertEquals(serverResponseAddLot.getResponseCode(), 200);

    RegisterBuyerDtoRequest dtoRequestBuyer = new RegisterBuyerDtoRequest(
            "Никитаа",
            "Асаевичъ",
            "qwertyuiopp",
            "firetreesdpoisiodi"
    );

        server.registerBuyer(gson.toJson(dtoRequestBuyer));

    ServerResponse serverResponseBuyer = server.loginUserBuyer
            (gson.toJson(new LoginDtoRequest
                    (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));

    TokenDtoResponse loginDtoResponseBuyer = gson.fromJson
            (serverResponseBuyer.getResponseData(), TokenDtoResponse.class);

    String buyerUuid = loginDtoResponseBuyer.getUuid();

    ServerResponse serverResponseAddPrice = server.addPrice(buyerUuid, gson.toJson
            (new AddPriceDtoRequest(200, 1)));
        Assertions.assertEquals
                (serverResponseAddPrice.getResponseData(), "This lot not found");
        Assertions.assertEquals(serverResponseAddPrice.getResponseCode(),400);
}

    @Test
    public void testDeleteWithoutAddAndAddPlusDeleteLot() {
        RegisterBuyerDtoRequest dtoRequest = new RegisterBuyerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "Nicoolenkosdasdqwfdfdfsads",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));
        ServerResponse serverResponse = server.loginUserBuyer
                (gson.toJson(new LoginDtoRequest(dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse loginDtoResponse = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);
        String uuid = loginDtoResponse.getUuid();

        DeleteLotDtoRequest deleteLotWithoutAddDtoRequest = new DeleteLotDtoRequest(1);
        ServerResponse deleteWithoutAddResponse = server.deleteLot
                (uuid, gson.toJson(deleteLotWithoutAddDtoRequest));
        Assertions.assertEquals(deleteWithoutAddResponse.getResponseCode(), 400);

        AddLotDtoRequest addLotDtoRequest = new AddLotDtoRequest
                ("Table", "good", 2000);

        ServerResponse addResponse = server.addLot(uuid, gson.toJson(addLotDtoRequest));
        Assertions.assertEquals(addResponse.getResponseCode(), 200);

        DeleteLotDtoRequest deleteLotDtoRequest = new DeleteLotDtoRequest(Integer.parseInt(addResponse.getResponseData()));

        ServerResponse deleteResponse = server.deleteLot(uuid, gson.toJson(deleteLotDtoRequest));
        Assertions.assertEquals(deleteResponse.getResponseCode(), 200);
        Assertions.assertEquals(deleteResponse.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
    }

    @Test
    public void testAddLotWithProblemData() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "jmishenko",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));
        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse loginDtoResponse = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);
        String uuid = loginDtoResponse.getUuid();

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
        RegisterBuyerDtoRequest dtoRequest = new RegisterBuyerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "weweweqwewqe",
                "firetreesd"
        );

        server.registerBuyer(gson.toJson(dtoRequest));
        ServerResponse serverResponse = server.loginUserBuyer
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse loginDtoResponse = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        String uuid = loginDtoResponse.getUuid();

        AddPriceDtoRequest priceDtoRequest = new AddPriceDtoRequest(2000, 1);

        ServerResponse addPriceServerResponse = server.addPrice(uuid, gson.toJson(priceDtoRequest));
        Assertions.assertEquals(addPriceServerResponse.getResponseData(), "This lot not found");
        Assertions.assertEquals(addPriceServerResponse.getResponseCode(), 400);

    }

    @Test
    public void testAddCategoryToLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        String uuid = response.getUuid();
        ServerResponse addLot  = server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(Integer.parseInt(addLot.getResponseData()), 1);

        ServerResponse addCategoryResponse = server.addCategoryToLot
                (uuid, gson.toJson(addCategoryToLotRequest));

        //В этом тесте нет категории которая вставлялась бы
        Assertions.assertEquals(addCategoryResponse.getResponseCode(), 400);
        Assertions.assertEquals(addCategoryResponse.getResponseData(),
                "This category not found");
    }

    @Test
    public void testAddCategoryToInexistedLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec2007",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        String uuid = response.getUuid();
        int idLot = Integer.parseInt(server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000))).getResponseData());

        ServerResponse serverAddCategory = server.addCategory(uuid, gson.toJson(new AddCategoryRequest("mebel")));

        AddCategoryResponse addCategoryResponse = gson.fromJson(serverAddCategory.getResponseData(), AddCategoryResponse.class);

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(idLot, addCategoryResponse.getId());

        ServerResponse addCategoryToLotResponse = server.addCategoryToLot
                (uuid, gson.toJson(addCategoryToLotRequest));
        Assertions.assertEquals(addCategoryToLotResponse.getResponseCode(), 200);
        Assertions.assertEquals(addCategoryToLotResponse.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
    }

    @Test
    public void testAddInexistedCategoryToLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec2007",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        String uuid = response.getUuid();

        int idLot = Integer.parseInt(server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000))).getResponseData());

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(idLot, 2);

        ServerResponse addCategoryToLotResponse = server.addCategoryToLot
                (uuid, gson.toJson(addCategoryToLotRequest));
        Assertions.assertEquals(addCategoryToLotResponse.getResponseCode(), 400);
        Assertions.assertEquals(addCategoryToLotResponse.getResponseData(), "This category not found");
    }

    @Test
    public void testAddDuplicateCategoryToLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec2007",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        String uuid = response.getUuid();

        int idLot = Integer.parseInt(server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000))).getResponseData());

        ServerResponse serverAddCategory = server.addCategory(uuid, gson.toJson(new AddCategoryRequest("mebel")));

        AddCategoryResponse addCategoryResponse = gson.fromJson(serverAddCategory.getResponseData(), AddCategoryResponse.class);

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(idLot, addCategoryResponse.getId());


        server.addCategoryToLot(uuid, gson.toJson(addCategoryToLotRequest));

        ServerResponse addCategoryAgainResponse = server.addCategoryToLot
                (uuid, gson.toJson(addCategoryToLotRequest));

        Assertions.assertEquals(addCategoryAgainResponse.getResponseCode(), 400);
        Assertions.assertEquals(addCategoryAgainResponse.getResponseData(), "Lot already in this category");
    }

    @Test
    public void testDeleteInexistedCategoryFromLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        String uuid = response.getUuid();

        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        int idLot = Integer.parseInt(server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000))).getResponseData());

        ServerResponse serverAddCategory = server.addCategory(uuid, gson.toJson(new AddCategoryRequest("mebel")));

        AddCategoryResponse addCategoryResponse = gson.fromJson(serverAddCategory.getResponseData(), AddCategoryResponse.class);

        DeleteCategoryFromLotRequest request = new DeleteCategoryFromLotRequest(idLot, addCategoryResponse.getId());

        ServerResponse deleteInexistedCategoryResponse = server.deleteCategoryFromLot(uuid, gson.toJson(request));

        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseCode(), 400);
        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseData(), "This lot not found");
    }

    @Test
    public void testDeleteCategoryFromInexistedLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        String uuid = response.getUuid();

        server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));


        int idLot = Integer.parseInt(server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000))).getResponseData())+1;

        ServerResponse serverAddCategory = server.addCategory(uuid, gson.toJson(new AddCategoryRequest("mebel")));

        AddCategoryResponse addCategoryResponse = gson.fromJson(serverAddCategory.getResponseData(), AddCategoryResponse.class);

        DeleteCategoryFromLotRequest request = new DeleteCategoryFromLotRequest(idLot, addCategoryResponse.getId());

        ServerResponse deleteInexistedCategoryResponse = server.deleteCategoryFromLot(uuid, gson.toJson(request));

        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseCode(), 400);
        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseData(), "This lot not found");
    }


    @Test
    public void testDeleteCategoryFromLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        TokenDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        String uuid = response.getUuid();

        int idLot = Integer.parseInt(server.addLot(uuid, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000))).getResponseData());

        ServerResponse serverAddCategory = server.addCategory(uuid, gson.toJson(new AddCategoryRequest("mebel")));

        AddCategoryResponse addCategoryResponse = gson.fromJson(serverAddCategory.getResponseData(), AddCategoryResponse.class);

        AddCategoryToLotRequest addCategoryToLotRequest = new AddCategoryToLotRequest(idLot, addCategoryResponse.getId());
        server.addCategoryToLot(uuid, gson.toJson(addCategoryToLotRequest));

        DeleteCategoryFromLotRequest request = new DeleteCategoryFromLotRequest(idLot, addCategoryResponse.getId());

        ServerResponse deleteInexistedCategoryResponse = server.deleteCategoryFromLot(uuid, gson.toJson(request));

        Assertions.assertEquals(deleteInexistedCategoryResponse.getResponseCode(), 200);
        Assertions.assertEquals
                (deleteInexistedCategoryResponse.getResponseData(), gson.toJson(new EmptySuccessDtoResponse()));
    }

    @Test
    public void testGetInfoLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        RegisterBuyerDtoRequest dtoRequestBuyer = new RegisterBuyerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078ff",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        server.registerBuyer(gson.toJson(dtoRequestBuyer));

        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        ServerResponse serverResponseBuyer = server.loginUserBuyer
                (gson.toJson(new LoginDtoRequest
                        (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));

        TokenDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        TokenDtoResponse responseBuyer = gson.fromJson
                (serverResponseBuyer.getResponseData(), TokenDtoResponse.class);

        String uuidSeller = response.getUuid();
        String uuidBuyer = responseBuyer.getUuid();

        int idLot = Integer.parseInt(server.addLot(uuidSeller, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000))).getResponseData());

        GetLotInfoRequest getLotInfoRequest = new GetLotInfoRequest(idLot);

        ServerResponse getInfoResponse = server.getInfoLot(uuidSeller, gson.toJson(getLotInfoRequest));
        Assertions.assertEquals(getInfoResponse.getResponseCode(), 200);
    }

    @Test
    public void testGetInfoInexistedLot() {
        RegisterSellerDtoRequest dtoRequest = new RegisterSellerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078",
                "firetreesd"
        );

        RegisterBuyerDtoRequest dtoRequestBuyer = new RegisterBuyerDtoRequest(
                "Никитаа",
                "Асаевичъ",
                "prodavec20078ff",
                "firetreesd"
        );

        server.registerSeller(gson.toJson(dtoRequest));

        server.registerBuyer(gson.toJson(dtoRequestBuyer));

        ServerResponse serverResponse = server.loginUserSeller
                (gson.toJson(new LoginDtoRequest
                        (dtoRequest.getLogin(), dtoRequest.getPassword())));

        ServerResponse serverResponseBuyer = server.loginUserBuyer
                (gson.toJson(new LoginDtoRequest
                        (dtoRequestBuyer.getLogin(), dtoRequestBuyer.getPassword())));

        TokenDtoResponse response = gson.fromJson
                (serverResponse.getResponseData(), TokenDtoResponse.class);

        TokenDtoResponse responseBuyer = gson.fromJson
                (serverResponseBuyer.getResponseData(), TokenDtoResponse.class);

        String uuidSeller = response.getUuid();
        String uuidBuyer = responseBuyer.getUuid();

        server.addLot(uuidSeller, gson.toJson
                (new AddLotDtoRequest("Table", "good_quality", 2000)));

        GetLotInfoRequest getLotInfoRequest = new GetLotInfoRequest(2);
        ServerResponse getInfoResponse = server.getInfoLot(uuidSeller, gson.toJson(getLotInfoRequest));

        Assertions.assertEquals(getInfoResponse.getResponseCode(), 400);
    }

}

