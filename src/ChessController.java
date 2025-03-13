/**
 *
 * This is the SHITTIEST code ive ever written in my life. It works and IDK why.
 * It's SO inefficient.
 * Nothing works how it should work but have fun game logic
 *
 * - Shakil :) (im dying its 1am)
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Displays a chessboard and pieces on the board.
 *
 * @author Shakil Hussain
 */
public class ChessController extends Application {

    private static final int TILE_SIZE = 64; // Size of each tile on the chessboard
    private static final int BOARD_SIZE = 8; // Number of rows and columns on the board
    private static final String ASSETS_PATH = "file:diagrams/gui/assets/sprites/"; // Path to chess piece images

    // Initial chessboard layout
    private final String[][] board = {
            {"chessRookBlack", "chessKnightBlack", "chessBishopBlack", "chessQueenBlack", "chessKingBlack", "chessBishopBlack", "chessKnightBlack", "chessRookBlack"},
            {"chessPawnBlack", "chessPawnBlack", "chessPawnBlack", "chessPawnBlack", "chessPawnBlack", "chessPawnBlack", "chessPawnBlack", "chessPawnBlack"},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {"chessPawnWhite", "chessPawnWhite", "chessPawnWhite", "chessPawnWhite", "chessPawnWhite", "chessPawnWhite", "chessPawnWhite", "chessPawnWhite"},
            {"chessRookWhite", "chessKnightWhite", "chessBishopWhite", "chessQueenWhite", "chessKingWhite", "chessBishopWhite", "chessKnightWhite", "chessRookWhite"}
    };

    private int selectedX = -1, selectedY = -1;

    /**
     * Setting up the chessboard UI
     *
     * @author Shakil Hussain
     */
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawChessBoard(gc);
        drawPieces(gc);

        canvas.setOnMouseClicked(event -> handleMouseClick(event, gc));

        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Chess Board");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Draws the chessboard on the canvas
     *
     * @param gc GraphicsContext used for drawing
     *
     * @author Shakil Hussain
     */
    private void drawChessBoard(GraphicsContext gc) {
        boolean white = true;
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                gc.setFill(white ? Color.rgb(235, 235, 208) : Color.rgb(119, 148, 85));
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                white = !white;
            }
            white = !white;
        }
    }

    /**
     * Draws the chess pieces
     *
     * @param gc GraphicsContext used for drawing images
     *
     * @author Shakil Hussain
     */
    private void drawPieces(GraphicsContext gc) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                String pieceName = board[y][x];
                if (pieceName != null) {
                    Image pieceImage = new Image(ASSETS_PATH + pieceName + ".png", TILE_SIZE, TILE_SIZE, true, true);
                    gc.drawImage(pieceImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    /**
     * Handles mouse clicks for selecting and moving pieces
     *
     * @param event MouseEvent containing click coordinates
     * @param gc GraphicsContext used to redraw the board
     *
     * @author Shakil Hussain
     */
    private void handleMouseClick(MouseEvent event, GraphicsContext gc) {
        int x = (int) (event.getX() / TILE_SIZE);
        int y = (int) (event.getY() / TILE_SIZE);

        if (selectedX == -1 && selectedY == -1) {
            // Selecting a piece
            if (board[y][x] != null) {
                selectedX = x;
                selectedY = y;
            }
        } else {
            // Moving the selected piece
            board[y][x] = board[selectedY][selectedX];
            board[selectedY][selectedX] = null;
            selectedX = -1;
            selectedY = -1;
        }
        drawChessBoard(gc);
        drawPieces(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
