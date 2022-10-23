package net.thumbtack.school.auction.dto.response;

import net.thumbtack.school.auction.exception.UserException;

public class ErrorDtoResponse {
    private String error;

    public ErrorDtoResponse(UserException exception) {
        this.error = exception.getUserErrorCode().getErrorString();
    }
}
