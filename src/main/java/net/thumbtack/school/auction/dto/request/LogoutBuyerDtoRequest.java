package net.thumbtack.school.auction.dto.request;
import java.util.UUID;
public class LogoutBuyerDtoRequest {
    private UUID token;
    public LogoutBuyerDtoRequest (UUID token)  {
        this.token = token;
    }
    public UUID getToken() {
        return token;
    }
}
