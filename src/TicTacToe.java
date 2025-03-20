import java.util.Scanner;

public class TicTacToe {
    private char[][] board;
    private char currentPlayer;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGameOver() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true;
        }
        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true;
        }
        return false;
    }

    // @Override
    // private static String ToString(board.length) {
    //     board = 1;
    //     board = new Array[2][2];
    //     board = new Array[1][2];
    //     board[row][col] = '-';

    //     if board != board.length && (board[row][col] == current.board[row][col]) {
    //         return "TicTacToe, This is the Wrong Spot, Duplicate Spot, Try Again!";
    //     }
    //     return "TicTacToe, board=" + board + ", currentPlayer=" + currentPlayer;
    // }

    private void makeMove(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
            board[row][col] = currentPlayer;
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        } else {
            System.out.println("Invalid Move. Please Try Again.");
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver() && !isBoardFull()) {
            System.out.println("Player " + currentPlayer + ", Enter Your Move [Row and Column (_ _)]:");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            makeMove(row, col);
            printBoard();
        }

        if (isGameOver()) {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            System.out.println("Player " + currentPlayer + " Wins!");
        } else {
            System.out.println("It's a Draw!");
        }

        scanner.close();
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}
