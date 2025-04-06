package networkingObjectsSending;

import java.io.Serializable;

public class PracticeGameObj implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean win;
    private char[][] board;
    private String testString;

    public PracticeGameObj(boolean win, char[][] board, String testString) {
        this.win = win;
        this.board = board;
        this.testString = testString;
    }
    public String getTestString() {return testString;}
    public void setTestString(String testString) {this.testString = testString;}

    public char[][] getBoard() { return board; }
    public boolean isWin() { return win; }
    public void setWin(boolean win) { this.win = win; }
}