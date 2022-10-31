package net.thumbtack.school.auction.model;
import java.util.Objects;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String firstname;
    private String lastname;
    private String login;
    private String password;
}