package net.thumbtack.school.auction.server;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ServerResponse
{
    private int responseCode;
    private String responseData;

    public ServerResponse(int reCode, String reData) throws IllegalArgumentException {
        if(reCode == 200 || reCode == 400 || (reData != null && !reData.equals("")))
        {
            responseCode = reCode;
            responseData = reData;
        }
    }
}
