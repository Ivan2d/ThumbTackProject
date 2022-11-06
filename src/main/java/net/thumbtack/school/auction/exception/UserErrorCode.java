package net.thumbtack.school.auction.exception;

public enum UserErrorCode {

    EMPTY_FIRST_NAME("Empty first name"),
    EMPTY_LAST_NAME("Empty last name"),
    EMPTY_LOGIN("Empty login"),
    EMPTY_PASSWORD("Empty password"),
    SHORT_PASSWORD("Your password is shorter then 8 characters"),
    SHORT_LOGIN("Your login is shorter then 8 characters"),
    WRONG_LOGIN_OR_PASSWORD("This login or password is wrong"),
    DOUBLE_LOGIN("This login already existed"),
    TOKEN_NOT_FOUND("This token not exist"),
    SESSION_NOT_FOUND("Session not found"),

    LOT_NOT_FOUND("This lot not found"),
    WRONG_JSON("This json is wrong");

    private String message;

    private UserErrorCode(String message) {
        this.message = message;
    }

    public String getErrorString() {
        return message;
    }
}