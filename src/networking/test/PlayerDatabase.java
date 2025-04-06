import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PlayerDatabase { //Value: PlayerData object
    private HashMap<String, PlayerData> playerDatabase;

    public PlayerDatabase() {
        playerDatabase = new HashMap<>();
    }

    public void addPlayer(String globalUserId, PlayerData playerData) {
        playerDatabase.put(globalUserId, playerData);
    }

    public int getElo(String globalUserId, String gameMode) {
        PlayerData playerData = playerDatabase.get(globalUserId);
        if (playerData != null) {
            return playerData.getAPlayerElo(gameMode);
        }
        return -1;
    }

    public void setElo(String globalUserId, String gameMode, int newElo) {
        PlayerData playerData = playerDatabase.get(globalUserId);
        if (playerData != null) {
            playerData.setAPlayerElo(gameMode, newElo);
        }
    }

    public void printAllPlayers(int n) {
        int i = 0;
        for (String userId : playerDatabase.keySet()) {
            System.out.print("GlobalID: " + userId + " -> ");
            playerDatabase.get(userId).printPlayerData();
            i++;
            if (i > n){
                break;
            }
        }
    }

    public void removePlayer(String globalUserId) {
        playerDatabase.remove(globalUserId);
    }

    public PlayerData getPlayerData(String globalUserId) {
        return playerDatabase.get(globalUserId);
    }

    // I had ChatGTP give the solution for this method
    public List<PlayerData> findTopNPlayersByElo(int n, String gameMode) {
        List<PlayerData> playerList = new ArrayList<>(playerDatabase.values());

        playerList.sort(new Comparator<PlayerData>() {
            @Override
            public int compare(PlayerData p1, PlayerData p2) {
                int elo1 = p1.getAPlayerElo(gameMode);
                int elo2 = p2.getAPlayerElo(gameMode);
                return Integer.compare(elo2, elo1); // Higher ELO first
            }
        });

        List<PlayerData> topPlayers = new ArrayList<>();
        int topN = Math.min(n, playerList.size());

        for (int i = 0; i < topN; i++) {
            topPlayers.add(playerList.get(i));
        }

        return topPlayers;
    }
    // end of chatgtp
}