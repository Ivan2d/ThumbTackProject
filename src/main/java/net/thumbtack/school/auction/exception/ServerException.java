package net.thumbtack.school.auction.exception;

// REVU Вам не кажется, что пора его переименовать ?
// ServerException и ServerErrorCode
public class ServerException extends Exception {

    private ServerErrorCode serverErrorCode;

    public ServerErrorCode getUserErrorCode() {
        return serverErrorCode;
    }
    public ServerException(ServerErrorCode serverErrorCode) {
        this.serverErrorCode = serverErrorCode;
    }
}
