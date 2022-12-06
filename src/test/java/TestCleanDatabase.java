import net.thumbtack.school.auction.server.Server;
import org.junit.jupiter.api.BeforeEach;

public class TestCleanDatabase {
    Server server = new Server();

    @BeforeEach
    public void clearDataBase() {
        server.clear();
    }
}
