
import game.GamesEnum;
import org.junit.Before;
import player.Account;
import player.Player;

public class MatchOutcomeHandlerTest {

    private Player player1;
    private Player player2;

    @Before
    public void setUp() {
        Account account1 = new Account("Alice", 1);
        Account account2 = new Account("Bob", 2);

        // Set default ELO
        account1.updateElo(GamesEnum.CHESS, 1200);
        account2.updateElo(GamesEnum.CHESS, 1200);

        player1 = new Player(account1);
        player2 = new Player(account2);
    }

}
