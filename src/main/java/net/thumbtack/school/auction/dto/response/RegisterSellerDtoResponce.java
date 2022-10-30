package net.thumbtack.school.auction.dto.response;

import java.util.UUID;

public class RegisterSellerDtoResponce
{
    private UUID token;

    public RegisterSellerDtoResponce(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}