package net.thumbtack.school.auction.exception;

public enum UserErrorCode {

    EMPTY_FIRST_NAME("Empty first name"),
    EMPTY_LAST_NAME("Empty last name"),
    EMPTY_LOGIN("Empty login"),
    EMPTY_PASSWORD("Empty password"),
    DOUBLE_LOGIN("This login has been already used"),
    SHORT_PASSWORD("Your password is shorter then 8 characters"),
    SHORT_LOGIN("Your login is shorter then 6 characters"),
    WRONG_LOGIN_OR_PASSWORD("This login or password is wrong");

    private String message;

    private UserErrorCode(String message) {
        this.message = message;
    }

    public String getErrorString() {
        return message;
    }
}