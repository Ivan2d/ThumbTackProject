package net.thumbtack.school.auction.model;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    private String firstname;
    private String lastname;
    private String login;
    private String password;
}