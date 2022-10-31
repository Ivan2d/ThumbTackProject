package net.thumbtack.school.auction.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Service {
    private static final Gson GSON = new Gson();

    public static <T> T getObjectFromJson(String requestJsonString, Class<T> classOfT) throws UserException {
        try {
            if (isBlank(requestJsonString)) {
                throw new JsonSyntaxException("");
            }
            return GSON.fromJson(requestJsonString, classOfT);
        }
        catch(JsonSyntaxException ex) {
            throw new UserException(UserErrorCode.WRONG_JSON);
        }
    }
}

