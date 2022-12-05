package net.thumbtack.school.auction.exception;

public enum ServerErrorCode {

    EMPTY_FIRST_NAME("Empty first name"),
    EMPTY_LAST_NAME("Empty last name"),
    EMPTY_LOGIN("Empty login"),
    EMPTY_PASSWORD("Empty password"),
    SHORT_PASSWORD("Your password is shorter then 8 characters"),
    SHORT_LOGIN("Your login is shorter then 8 characters"),
    WRONG_LOGIN_OR_PASSWORD("This login or password is wrong"),
    DUPLICATE_LOGIN("This login already existed"),
    TOKEN_NOT_FOUND("This token not exist"),
    SESSION_NOT_FOUND("Session not found"),

    LOT_NOT_FOUND("This lot not found"),
    WRONG_JSON("This json is wrong"),
    USER_NOT_FOUND("This user don't exist"),
    NOT_A_SELLER("This user not seller"),
    ID_LESSER_THAN_ZERO("ID can't be < 0"),
    EMPTY_LIST("This list can't be empty"),
    EMPTY_DESCRIPTION("Empty description"),
    VALUE_LESSER_THAN_ZERO("Value can't be < 0"),
    ID_NOT_EXIST("ID not exist");

    private String message;

    private ServerErrorCode(String message) {
        this.message = message;
    }

    public String getErrorString() {
        return message;
    }
}