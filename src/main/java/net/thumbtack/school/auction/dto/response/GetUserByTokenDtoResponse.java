package net.thumbtack.school.auction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserByTokenDtoResponse {
    private String firstname;
    private String lastname;
    private String login;
    private String password;
}
