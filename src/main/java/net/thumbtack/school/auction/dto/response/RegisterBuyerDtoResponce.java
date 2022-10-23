package net.thumbtack.school.auction.dto.response;

import java.util.UUID;

public class RegisterBuyerDtoResponce {
    private UUID token;

    public RegisterBuyerDtoResponce(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
