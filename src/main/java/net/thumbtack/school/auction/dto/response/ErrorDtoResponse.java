package net.thumbtack.school.auction.dto.response;

import net.thumbtack.school.auction.exception.ServerException;

public class ErrorDtoResponse {
    private String error;

    public ErrorDtoResponse(ServerException exception) {
        this.error = exception.getUserErrorCode().getErrorString();
    }
}
