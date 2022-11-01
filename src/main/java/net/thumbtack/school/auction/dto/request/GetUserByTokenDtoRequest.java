package net.thumbtack.school.auction.dto.request;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetUserByTokenDtoRequest {
    private UUID uuid;
}
