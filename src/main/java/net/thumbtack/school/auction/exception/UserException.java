package net.thumbtack.school.auction.exception;

// REVU Вам не кажется, что пора его переименовать ?
// ServerException и ServerErrorCode
public class UserException extends Exception {

    private UserErrorCode userErrorCode;

    public UserErrorCode getUserErrorCode() {
        return userErrorCode;
    }
    public  UserException (UserErrorCode userErrorCode) {
        this.userErrorCode = userErrorCode;
    }
}
