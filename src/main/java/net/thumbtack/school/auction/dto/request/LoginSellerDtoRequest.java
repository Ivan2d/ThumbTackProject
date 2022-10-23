package net.thumbtack.school.auction.dto.request;

public class LoginSellerDtoRequest {
    private String login;
    private String password;

    public LoginSellerDtoRequest (String login, String password) {
        setLogin(login);
        setPassword(password);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
