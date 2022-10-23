package net.thumbtack.school.auction.dto.response;
import net.thumbtack.school.auction.ServerResponse;

public class LogoutBuyerDtoResponce {
    private ServerResponse responce;

    public LogoutBuyerDtoResponce(ServerResponse response){
        this.responce = response;
    }

    public ServerResponse getResponce() {
        return responce;
    }

    public void setResponce(ServerResponse responce) {
        this.responce = responce;
    }
}
