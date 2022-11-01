package net.thumbtack.school.auction.model;
import lombok.*;
@Data
@NoArgsConstructor
public class User {
    private String firstname;
    private String lastname;
    private String login;
    private String password;

    // REVU @AllArgsConstructor и уберите его
    public User(String firstname, String lastname, String login, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
    }
}