package net.thumbtack.school.auction.service;
import com.google.gson.Gson;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ServiceUtils {
    private static final Gson GSON = new Gson();

    public static <T> T getObjectFromJson(String requestJsonString, Class<T> classOfT) throws UserException {
            if (isBlank(requestJsonString)) {
                throw new UserException(UserErrorCode.WRONG_JSON);
            }
            return GSON.fromJson(requestJsonString, classOfT);
    }
}

