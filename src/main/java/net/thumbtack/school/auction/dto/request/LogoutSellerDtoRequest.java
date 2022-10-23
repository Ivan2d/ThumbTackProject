package net.thumbtack.school.auction.dto.request;

import java.util.UUID;

public class LogoutSellerDtoRequest {
    private UUID token;
    public LogoutSellerDtoRequest (UUID token)  {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }
}
