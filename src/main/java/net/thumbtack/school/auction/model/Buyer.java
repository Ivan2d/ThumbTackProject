package net.thumbtack.school.auction.model;

public class Buyer {
    private String firstName;
    private String login;
    private String password;

    public Buyer(String firstName, String login, String password) {
        this.firstName = firstName;
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


