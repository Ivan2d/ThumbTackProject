package net.thumbtack.school.auction.dto.request;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserByTokenDtoRequest {
    private UUID uuid;
}
