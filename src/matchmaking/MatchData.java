package matchmaking;

import game.GameType;

public class MatchData {
    private GameType gameType;
    private boolean affectsElo;
    private int selfID;
    private String selfUsername;
    private int selfElo;
    private String selfNetworkingInformation;
    private int selfPlayerNo;
    private int opponentID;
    private String opponentUsername;
    private int opponentElo;
    private String opponentNetworkingInformation;
    private int opponentPlayerNo;

    // constructor for matchdata. please god let this work
    public MatchData(GameType gameType, boolean affectsElo, int selfID, String selfUsername,
                     int selfElo, String selfNetworkingInformation, int selfPlayerNo,
                     int opponentID, String opponentUsername, int opponentElo,
                     String opponentNetworkingInformation, int opponentPlayerNo) {
        this.gameType = gameType;
        this.affectsElo = affectsElo;
        this.selfID = selfID;
        this.selfUsername = selfUsername;
        this.selfElo = selfElo;
        this.selfNetworkingInformation = selfNetworkingInformation;
        this.selfPlayerNo = selfPlayerNo;
        this.opponentID = opponentID;
        this.opponentUsername = opponentUsername;
        this.opponentElo = opponentElo;
        this.opponentNetworkingInformation = opponentNetworkingInformation;
        this.opponentPlayerNo = opponentPlayerNo;
    }

    // TODO: need to create getters for each of these ...

    public String getSelfUsername() {
        return selfUsername;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public int getSelfPlayerNo() {
        return selfPlayerNo;
    }

    public int getOpponentPlayerNo() {
        return opponentPlayerNo;
    }

}