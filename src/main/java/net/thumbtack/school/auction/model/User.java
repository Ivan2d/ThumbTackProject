package net.thumbtack.school.auction.model;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private int id = -1;
    public User(String firstname, String lastname, String login, String password){
        setFirstname(firstname);
        setLastname(lastname);
        setLogin(login);
        setPassword(password);
        id++;
    }
}