package net.thumbtack.school.auction.dto.response;
import java.util.UUID;
public class LoginBuyerDtoResponse {
    private UUID token;
    public LoginBuyerDtoResponse(UUID token)
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
