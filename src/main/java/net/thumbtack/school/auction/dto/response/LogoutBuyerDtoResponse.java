package net.thumbtack.school.auction.dto.response;
import net.thumbtack.school.auction.ServerResponse;

public class LogoutBuyerDtoResponse {
    private ServerResponse response;

    public LogoutBuyerDtoResponse(ServerResponse response){
        this.response = response;
    }

    public ServerResponse getResponse() {
        return response;
    }

    public void setResponse(ServerResponse response) {
        this.response = response;
    }
}
