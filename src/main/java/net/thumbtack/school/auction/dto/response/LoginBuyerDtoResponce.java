package net.thumbtack.school.auction.dto.response;
import java.util.UUID;
public class LoginBuyerDtoResponce {
    private UUID token;
    public LoginBuyerDtoResponce(UUID token)
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
