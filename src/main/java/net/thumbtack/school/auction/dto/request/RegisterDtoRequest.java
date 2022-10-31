package net.thumbtack.school.auction.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDtoRequest {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}
