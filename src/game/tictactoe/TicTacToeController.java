package game.tictactoe; /**
 * This is probably equally bad as my checkers code but hey, it's Tic Tac Toe!
 * X's and O's everywhere, someone wins (or not), what more do you want?
 *
 * - Shakil :) (still sleep deprived)
 */

 import javafx.application.Application;
 import javafx.scene.Scene;
 import javafx.scene.canvas.Canvas;
 import javafx.scene.canvas.GraphicsContext;
 import javafx.scene.control.Button;
 import javafx.scene.input.MouseEvent;
 import javafx.scene.layout.Pane;
 import javafx.scene.paint.Color;
 import javafx.stage.Stage;

 /**
  * Displays a Tic Tac Toe board and handles game logic.
  */
 public class TicTacToeController extends Application {

     private static final int TILE_SIZE = 100;
     private static final int BOARD_SIZE = 3;
     private static final int BOARD_PADDING = 20;

     // 0 = empty, 1 = X, 2 = O
     private final int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
     private int currentPlayer = 1; // Start with X
     private boolean gameOver = false;

     @Override
     public void start(Stage primaryStage) {
         Pane root = new Pane();
         Canvas canvas = new Canvas(
             BOARD_SIZE * TILE_SIZE + 2 * BOARD_PADDING,
             BOARD_SIZE * TILE_SIZE + 2 * BOARD_PADDING
         );
         GraphicsContext gc = canvas.getGraphicsContext2D();

         drawBoard(gc);

         canvas.setOnMouseClicked(event -> {
             if (!gameOver) {
                 handleMouseClick(event, gc);
             }
         });

         // Create buttons
         Button offerDrawButton = new Button("Offer Draw");
         offerDrawButton.setLayoutX(650);
         offerDrawButton.setLayoutY(10);
         offerDrawButton.setOnAction(e -> handleOfferDraw());

         Button resignButton = new Button("Resign");
         resignButton.setLayoutX(650);
         resignButton.setLayoutY(50);
         resignButton.setOnAction(e -> handleResign());

         root.getChildren().addAll(canvas, offerDrawButton, resignButton);
         Scene scene = new Scene(root, 800, 600);

         primaryStage.setTitle("Tic Tac Toe");
         primaryStage.setScene(scene);
         primaryStage.setResizable(false);
         primaryStage.show();
     }

     private void drawBoard(GraphicsContext gc) {
         // Clear board
         gc.setFill(Color.WHITE);
         gc.fillRect(0, 0,
             BOARD_SIZE * TILE_SIZE + 2 * BOARD_PADDING,
             BOARD_SIZE * TILE_SIZE + 2 * BOARD_PADDING
         );

         // Draw grid
         gc.setStroke(Color.BLACK);
         gc.setLineWidth(3);

         // Vertical lines
         for (int i = 1; i < BOARD_SIZE; i++) {
             gc.strokeLine(
                 BOARD_PADDING + i * TILE_SIZE, BOARD_PADDING,
                 BOARD_PADDING + i * TILE_SIZE, BOARD_PADDING + BOARD_SIZE * TILE_SIZE
             );
         }

         // Horizontal lines
         for (int i = 1; i < BOARD_SIZE; i++) {
             gc.strokeLine(
                 BOARD_PADDING, BOARD_PADDING + i * TILE_SIZE,
                 BOARD_PADDING + BOARD_SIZE * TILE_SIZE, BOARD_PADDING + i * TILE_SIZE
             );
         }

         // Draw X's and O's
         for (int row = 0; row < BOARD_SIZE; row++) {
             for (int col = 0; col < BOARD_SIZE; col++) {
                 if (board[row][col] == 1) {
                     drawX(gc, col, row);
                 } else if (board[row][col] == 2) {
                     drawO(gc, col, row);
                 }
             }
         }
     }

     private void drawX(GraphicsContext gc, int col, int row) {
         gc.setStroke(Color.RED);
         gc.setLineWidth(4);

         double padding = 15;
         double x = BOARD_PADDING + col * TILE_SIZE;
         double y = BOARD_PADDING + row * TILE_SIZE;

         // Draw X
         gc.strokeLine(x + padding, y + padding, x + TILE_SIZE - padding, y + TILE_SIZE - padding);
         gc.strokeLine(x + TILE_SIZE - padding, y + padding, x + padding, y + TILE_SIZE - padding);
     }

     private void drawO(GraphicsContext gc, int col, int row) {
         gc.setStroke(Color.BLUE);
         gc.setLineWidth(4);

         double padding = 15;
         double x = BOARD_PADDING + col * TILE_SIZE + TILE_SIZE/2;
         double y = BOARD_PADDING + row * TILE_SIZE + TILE_SIZE/2;

         // Draw O
         gc.strokeOval(
             x - (TILE_SIZE/2 - padding),
             y - (TILE_SIZE/2 - padding),
             TILE_SIZE - 2*padding,
             TILE_SIZE - 2*padding
         );
     }

     private void handleMouseClick(MouseEvent event, GraphicsContext gc) {
         int col = (int) ((event.getX() - BOARD_PADDING) / TILE_SIZE);
         int row = (int) ((event.getY() - BOARD_PADDING) / TILE_SIZE);

         // Check if click is within board bounds
         if (col >= 0 && col < BOARD_SIZE && row >= 0 && row < BOARD_SIZE) {
             // Check if cell is empty
             if (board[row][col] == 0) {
                 board[row][col] = currentPlayer;

                 if (checkWin(row, col)) {
                     gameOver = true;
                     System.out.println("Player " + (currentPlayer == 1 ? "X" : "O") + " wins!");
                 } else if (isBoardFull()) {
                     gameOver = true;
                     System.out.println("It's a draw!");
                 } else {
                     // Switch players
                     currentPlayer = currentPlayer == 1 ? 2 : 1;
                 }

                 drawBoard(gc);
             }
         }
     }

     private boolean checkWin(int row, int col) {
         return checkRow(row) || checkColumn(col) || checkDiagonals();
     }

     private boolean checkRow(int row) {
         return board[row][0] == currentPlayer &&
                board[row][1] == currentPlayer &&
                board[row][2] == currentPlayer;
     }

     private boolean checkColumn(int col) {
         return board[0][col] == currentPlayer &&
                board[1][col] == currentPlayer &&
                board[2][col] == currentPlayer;
     }

     private boolean checkDiagonals() {
         // Check main diagonal
         boolean mainDiagonal = board[0][0] == currentPlayer &&
                               board[1][1] == currentPlayer &&
                               board[2][2] == currentPlayer;

         // Check anti-diagonal
         boolean antiDiagonal = board[0][2] == currentPlayer &&
                               board[1][1] == currentPlayer &&
                               board[2][0] == currentPlayer;

         return mainDiagonal || antiDiagonal;
     }

     private boolean isBoardFull() {
         for (int row = 0; row < BOARD_SIZE; row++) {
             for (int col = 0; col < BOARD_SIZE; col++) {
                 if (board[row][col] == 0) {
                     return false;
                 }
             }
         }
         return true;
     }

     private boolean draw() {

        return isBoardFull();
     }

     private void handleOfferDraw() {
         // Handle draw offer logic here, e.g., show a message or ask for confirmation
         System.out.println("Draw offer sent!");
     }

     private void handleResign() {
         // Handle resignation logic here, e.g., end the game or show a resignation message
         System.out.println("You resigned!");
     }

     private void drawGame() {
            draw();
            System.out.println("Draw Game!");
     }

     public static void main(String[] args) {
         launch(args);
     }
 }