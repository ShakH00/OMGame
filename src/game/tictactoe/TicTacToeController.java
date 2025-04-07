package game.tictactoe;

/**
 * This is probably equally bad as my checkers code but hey, it's Tic Tac Toe!
 * X's and O's everywhere, someone wins (or not), what more do you want?
 *
 * - Shakil :) (still sleep deprived)
 */

import game.GameState;
import game.pieces.PieceType;
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
    private boolean gameOver = false;

    public static TicTacToe game = new TicTacToe();

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
                if(game.getBoard().getBoardState()[row][col] != null)
                {
                    if (game.getBoard().getBoardState()[row][col].getPieceType() == PieceType.LIGHT) {
                        drawX(gc, row, col);
                    } else if (game.getBoard().getBoardState()[row][col].getPieceType() == PieceType.DARK) {
                        drawO(gc, row, col);
                    }
                }
            }
        }
    }

    private void drawX(GraphicsContext gc, int row, int col) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(4);

        double padding = 15;
        double x = BOARD_PADDING + row * TILE_SIZE;
        double y = BOARD_PADDING + col * TILE_SIZE;

        // Draw X
        gc.strokeLine(x + padding, y + padding, x + TILE_SIZE - padding, y + TILE_SIZE - padding);
        gc.strokeLine(x + TILE_SIZE - padding, y + padding, x + padding, y + TILE_SIZE - padding);
    }

    private void drawO(GraphicsContext gc, int row, int col) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(4);

        double padding = 15;
        double x = BOARD_PADDING + row * TILE_SIZE + TILE_SIZE / 2;
        double y = BOARD_PADDING + col * TILE_SIZE + TILE_SIZE / 2;

        // Draw O
        gc.strokeOval(
            x - (TILE_SIZE / 2 - padding),
            y - (TILE_SIZE / 2 - padding),
            TILE_SIZE - 2 * padding,
            TILE_SIZE - 2 * padding
        );
    }

    private void handleMouseClick(MouseEvent event, GraphicsContext gc) {
        int row = (int) ((event.getX() - BOARD_PADDING) / TILE_SIZE);
        int col = (int) ((event.getY() - BOARD_PADDING) / TILE_SIZE);

        if(game.getGameState().equals(GameState.SETUP))
        {
            game.start();
            game.move(game.piece1, row, col);
            game.nextTurn();
        }

        else if(game.getGameState().equals(GameState.P1_TURN))
        {
            game.move(game.piece1, row, col);
            game.nextTurn();
        }

        else if(game.getGameState().equals(GameState.P2_TURN))
        {
            game.move(game.piece2, row, col);
            game.nextTurn();
        }

        redrawBoard(gc);
    }

    private void redrawBoard(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 600);
        drawBoard(gc);
    }

    private void handleOfferDraw() {
        // Handle draw offer logic here, e.g., show a message or ask for confirmation
        System.out.println("Draw offer sent!");
    }

    private void handleResign() {
        // Handle resignation logic here, e.g., end the game or show a resignation message
        System.out.println("You resigned!");
        game.surrender();
    }

    public static void main(String[] args) {
        launch(args);
    }
}