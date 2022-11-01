package net.thumbtack.school.auction.dto.response;
import lombok.*;
import net.thumbtack.school.auction.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetUserByTokenResponse {
    private User user;
}
