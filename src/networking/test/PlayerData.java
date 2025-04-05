import java.io.Serializable; // for ObjectOutputStream to not have any class version mismatch
import java.util.HashMap;


public class PlayerData implements Serializable{
    private static final long serialVersionUID = 1L;
    // for ObjectOutputStream to not have any class version mismatch
    private int userId;
    private String username;
    private HashMap<String, Integer> playerElos;
    private String gameModeInterested;

    public PlayerData(int userId, String username, String gameModeInterested) {
        this.userId = userId;
        this.username = username;
        this.playerElos = new HashMap<>();
        this.playerElos.put("Chess", 20);
        this.playerElos.put("Checker", 20);
        this.playerElos.put("Connect4", 20);
        this.playerElos.put("TicTacToe", 20);

        this.gameModeInterested = gameModeInterested;
    }
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public HashMap<String, Integer> getAllPlayerElos() {
        return playerElos;
    }

    public String getAllPlayerElosString() {
       return playerElos.toString();
    }

    public int getAPlayerElo(String gameMode) {
        return playerElos.get(gameMode);
    }

    public void setAPlayerElo(String gameMode, int change) {
        if(playerElos.get(gameMode) != null) {
            this.playerElos.put(gameMode, change);
        }
    }

    public String getGameModeInterested() {return gameModeInterested;}
    public void setGameModeInterested(String gameMode) {this.gameModeInterested = gameMode;}

    public void printPlayerData() {
        System.out.print("PlayerData = ");
        System.out.print("[Username: " + username);
        System.out.print(", ChessELO: " + playerElos.get("Chess"));
        System.out.print(", checkerElo: " + playerElos.get("Checker"));
        System.out.print(", connect4Elo: " + playerElos.get("Connect4"));
        System.out.print(", tictactoeElo: " + playerElos.get("TicTacToe"));
        System.out.println(", gameModeInterested: " + gameModeInterested + "]");
    }
}

