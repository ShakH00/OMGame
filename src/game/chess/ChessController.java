package game.chess; /**
 *
 * This is the SHITTIEST code ive ever written in my life. It works and IDK why.
 * It's SO inefficient.
 * Nothing works how it should work but have fun game logic
 *
 * - Shakil :) (im dying its 1am)
 */

import game.Board;
import game.GameType;
import game.checkers.Checkers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
    private Board board;
    private Chess chessGame;
    private int selectedX = -1, selectedY = -1;

    // Initial chessboard layout
//    private final String[][] board = {
//            {"blackRookChess", "blackKnightChess", "blackBishopChess", "blackQueenChess", "blackKingChess", "blackBishopChess", "blackKnightChess", "blackRookChess"},
//            {"blackPawnChess", "blackPawnChess", "blackPawnChess", "blackPawnChess", "blackPawnChess", "blackPawnChess", "blackPawnChess", "blackPawnChess"},
//            {null, null, null, null, null, null, null, null},
//            {null, null, null, null, null, null, null, null},
//            {null, null, null, null, null, null, null, null},
//            {null, null, null, null, null, null, null, null},
//            {"whitePawnChess", "whitePawnChess", "whitePawnChess", "whitePawnChess", "whitePawnChess", "whitePawnChess", "whitePawnChess", "whitePawnChess"},
//            {"whiteRookChess", "whiteKnightChess", "whiteBishopChess", "whiteQueenChess", "whiteKingChess", "whiteBishopChess", "whiteKnightChess", "whiteRookChess"}
//    };


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

        board = new Board(GameType.CHESS);
        chessGame = new Chess(); // Initialize Checkers game logic
        chessGame.setBoard(board); // Set the board in the checkers class.
        chessGame.start();

        drawChessBoard(gc);
        drawPieces(gc);

        canvas.setOnMouseClicked(event -> handleMouseClick(event, gc));

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
                String pieceName = "Pawn";
                // String pieceName = board[y][x];
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

    private void handleOfferDraw() {
        // Handle draw offer logic here, e.g., show a message or ask for confirmation
        System.out.println("Draw offer sent!");
    }

    private void handleResign() {
        // Handle resignation logic here, e.g., end the game or show a resignation message
        System.out.println("You resigned!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
