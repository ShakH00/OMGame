package networking;

import networking.prototypes.PlayerT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//TEST: higher use of chatgtp here for the speedier tasting
// and IntelliJ's generate test for methode test stubs was used
class PlayerTTest {

    private PlayerT player;

    @BeforeEach
    void setUp() {
        player = new PlayerT(800, 400);
        // will crash because the rest of the setup is not here,
        //but pass these test
    }

    @Test
    void playerMenu() {
        player.playerMenu();
        assertTrue(player.isVisible(), "playerMenu visible?");
    }

    @Test
    void setUpGUII() {
        player.setUpGUII();
        assertTrue(player.isVisible(), "Game GUI visible?");
    }

    // ASKED CHATGTP FOR THESE TEST


    @Test
    void connectToServer() {
        player.connectToServer(); // stub method
        assertTrue(true); // Nothing to assert since it's stubbed — just make sure it doesn't crash
    }

    @Test
    void setUpMenuButtons() {
        assertDoesNotThrow(() -> player.setUpMenuButtons());
    }

    @Test
    void setUpGame1Buttons() {
        assertDoesNotThrow(() -> player.setUpGame1Buttons());
    }

    @Test
    void main() {
        assertDoesNotThrow(() -> PlayerT.main(new String[0]));
    }
    //End of chatGTP
}