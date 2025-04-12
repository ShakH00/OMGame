package statistics;

import account.statistics.MatchOutcomeHandler;
import account.statistics.MatchOutcomeInvalidError;
import account.statistics.StatisticType;
import game.GameType;
import account.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MatchOutcomeHandlerTest {
    private Account player1;
    private Account player2;
    private HashMap<StatisticType, Integer> player1Stats;
    private HashMap<StatisticType, Integer> player2Stats;

}
