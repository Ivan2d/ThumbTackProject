package net.thumbtack.school.auction.dto.response;

import net.thumbtack.school.auction.ServerResponse;

public class LogoutSellerDtoResponse {
    private ServerResponse responce;

    public LogoutSellerDtoResponse(ServerResponse response){
        this.responce = response;
    }

    public ServerResponse getResponce() {
        return responce;
    }

    public void setResponce(ServerResponse responce) {
        this.responce = responce;
    }
}
