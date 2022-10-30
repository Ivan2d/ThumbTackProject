package net.thumbtack.school.auction.dto.request;

public class RegisterBuyerDtoRequest {
    private String firstName;

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String lastName;
    private String login;
    private String password;

    public RegisterBuyerDtoRequest(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public String getLastName() {
        return lastName;
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