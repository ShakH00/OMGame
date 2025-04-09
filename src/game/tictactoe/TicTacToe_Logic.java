//package game.tictactoe;
//
//import game.GameState;
//import game.GameType;
//
//import java.util.Scanner;
//
//public class TicTacToe_Logic {
//
//    // Create a 3x3 Tic-Tac-Toe game.Board with char
//    private char[][] board;
//    private char currentPlayer;
//    private GameState gameState;
//    private GameType gameType = GameType.TICTACTOE;
//
//    /*
//     * game.tictactoe.TicTacToe Constructor
//     * Initializes the board and sets the first player(current player) to 'X'
//     * Calls initializeBoard() to set cells
//     */
//    public TicTacToe_Logic() {
//        board = new char[3][3];
//        currentPlayer = 'X';
//        initializeBoard();
//    }
//
//    /*
//     * Initializes the whole board with '-'
//     * To represent empty cells
//     */
//    private void initializeBoard() {
//        for (int rows = 0; rows < 3; rows++) {
//            for (int columns = 0; columns < 3; columns++) {
//                board[rows][columns] = '-';
//            }
//
//        }
//    }
//
//    /*
//     * Prints the current state of the board
//     * 3x3 grid
//     */
//    private void printBoard() {
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
//
//    /*
//     * Checks if the board is full
//     * If there are no '-', the board is full
//     */
//    private boolean isBoardFull() {
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (board[i][j] == '-') {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    /*
//     * Checks if the game is over
//     * Calls checkRows(), checkColumns(), checkDiagonals()
//     */
//    private boolean isGameOver() {
//        return checkRows() || checkColumns() || checkDiagonals();
//    }
//
//    /*
//     * Checks if any row has the same elements
//     * If all elements in a row are the same, return true
//     */
//    private boolean checkRows() {
//        for (int i = 0; i < 3; i++) {
//            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /*
//     * Checks if any column has the same elements
//     * If all elements in a column are the same, return true
//     */
//    private boolean checkColumns() {
//        for (int i = 0; i < 3; i++) {
//            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /*
//     * Checks if any diagonal has the same elements
//     * If all elements in a diagonal are the same, return true
//     */
//    private boolean checkDiagonals() {
//
//        // Top Left to Bottom Right
//        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
//            return true;
//        }
//
//        // Top Right to Bottom Left
//        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
//            return true;
//        }
//
//        // No Diagonal Win
//        return false;
//    }
//
//    /*
//     * Makes a move on the board
//     * If the cell is empty, place the current player's symbol
//     * Invalid Move:
//     *      If the cell is not empty, print "Invalid Move, Outside of game.Board. Please Try Again."
//     *      Or If the placed cell is outside of the board, print "Invalid Move, Cell is Occupied. Please Try Again."
//     */
//    private void makeMove(int row, int col) {
//        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
//            board[row][col] = currentPlayer;
//            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
//        } else if (row >= 3 || col >= 3 || row < 0 || col < 0) {
//            System.out.println("Invalid Move, Outside of game.Board. Please Try Again.");
//        } else {
//            System.out.println("Invalid Move, Cell is Occupied. Please Try Again.");
//        }
//    }
//
//    /*
//     * Main Game Loop
//     * Asks the current player to make a move
//     * Calls makeMove() and printBoard()
//     * If the game is over, print the winner
//     * If the board is full, print "It's a Draw!"
//     */
//    public void play() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome to Tic Tac Toe!");
//
//        while (!isGameOver() && !isBoardFull()) {
//            gameState = (currentPlayer == 'X') ? GameState.P1_TURN : GameState.P2_TURN;
//            System.out.println("game.Player " + currentPlayer + ", Enter Your Move:");
//
//            System.out.print("Row [0, 1, 2]: ");
//            int row = scanner.nextInt();
//            System.out.print("Column [0, 1, 2]: ");
//            int col = scanner.nextInt();
//            makeMove(row, col);
//            printBoard();
//        }
//
//        if (isGameOver()) {
//            if(currentPlayer == 'X') {
//                gameState = GameState.P1_WIN;
//            } else {
//                gameState = GameState.P2_WIN;
//            }
//            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
//            System.out.println("GameOver! Congratulations,");
//            System.out.println("game.Player " + currentPlayer + " Wins!");
//        } else {
//            System.out.println("GameOver!");
//            System.out.println("It's a Draw!");
//        }
//
//        scanner.close();
//    }
//
//    /*
//     * Main Method
//     * Creates a new game.tictactoe.TicTacToe object and calls play()
//     */
//    public static void main(String[] args) {
//        TicTacToe_Logic game = new TicTacToe_Logic();
//        game.play();
//    }
//}
