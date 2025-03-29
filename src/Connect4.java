public class Connect4 {
    private Player p1;
    private Player p2;
    private Board board;

    public Connect4() {
        p1 = new Player();
        p2 = new Player();

        board = new Board(GameType.CONNECT4);
    }

}
