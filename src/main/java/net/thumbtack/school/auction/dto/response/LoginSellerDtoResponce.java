package net.thumbtack.school.auction.dto.response;

import java.util.UUID;

public class LoginSellerDtoResponce
{
    private UUID token;

    public LoginSellerDtoResponce(UUID token)
    {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

}
