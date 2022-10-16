package net.thumbtack.school.auction.model;

public class Seller extends User {
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public Seller(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
