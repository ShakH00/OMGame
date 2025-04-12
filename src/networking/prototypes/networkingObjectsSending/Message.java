package networking.prototypes.networkingObjectsSending;

public class Message {
    int playerNum;
    String message;

    public Message(int playerNum, String message) {
        this.playerNum = playerNum;
        this.message = message;
    }

    public int getPlayerNum() {return playerNum;}
    public String getMessage() {return message;}
    public void setPlayerNum(int playerNum) {this.playerNum = playerNum;}
    public void setMessage(String message) {this.message = message;}
}
