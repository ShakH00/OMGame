package game.chess; /**
 *
 * This is the SHITTIEST code ive ever written in my life. It works and IDK why.
 * It's SO inefficient.
 * Nothing works how it should work but have fun game logic
 *
 * - Shakil :) (im dying its 1am)
 */

import game.Board;
import game.GameState;
import game.GameType;
import game.Player;
import game.checkers.Checkers;
import game.checkers.CheckersPiece;
import game.pieces.MovingPiece;
import game.pieces.Piece;
import game.pieces.PieceType;
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
 * @author Shakil Hussain, Abdulrahman Negmeldin
 */
public class ChessController extends Application {

    private static final int TILE_SIZE = 64; // Size of each tile on the chessboard
    private static final int BOARD_SIZE = 8; // Number of rows and columns on the board
    private static final String ASSETS_PATH = "file:diagrams/gui/assets/sprites/"; // Path to chess piece images
    private Board board;
    private Chess chessGame;
    private int selectedX = -1, selectedY = -1;
    //private final Player player1 = new Player();
    //private final Player player2 = new Player();

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
     * @author Shakil Hussain, Abdulrahman Negmeldin
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
     * A basic helper method used to determine path to piece image
     * @param piece: piece being drawn on board
     * @return String colour
     * @author Abdulrahman Negmeldin
     */
    public String colorToString(MovingPiece piece){
        Color color = piece.getColor();
        if(color.equals(Color.WHITE)){
            return "white";
        } else if(color.equals(Color.BLACK)){
            return "black";
        }
        return null;
    }

    /**
     * A helper method used to capitalize the first letter of a string
     * Used for determing image path
     * @param str: String that will have its first character capitalized
     * @return String where first character is capitalized
     */
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Draws the chess pieces
     *
     * @param gc GraphicsContext used for drawing images
     *
     * @author Abdulrahman Negmeldin, base code by Shakil Hussain which used string pieces instead
     */
    private void drawPieces(GraphicsContext gc) {
        Piece[][] chessBoard = board.getBoardState();
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if(chessBoard[x][y] != null){
                    MovingPiece piece = (MovingPiece) chessBoard[x][y];
                    String color = colorToString(piece);
                    String path = color + capitalizeFirstLetter(piece.getClass().toString().toLowerCase().replace("class game.chess.","")) + "Chess";
                    Image pieceImage = new Image(ASSETS_PATH + path + ".png", TILE_SIZE, TILE_SIZE, true, true);
                    gc.drawImage(pieceImage, y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE);

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
     * @author Shakil Hussain, Abdulrahman Negmeldin
     * Modified by Abdulrahman Negmeldin to use actual pieces not strings and check for piece pinning
     * Also modified to check for which player's turn it is and check if game ends
     */
    private void handleMouseClick(MouseEvent event, GraphicsContext gc) {
        int x = (int) (event.getX() / TILE_SIZE);
        int y = (int) (event.getY() / TILE_SIZE);
        Piece[][] chessBoard = board.getBoardState();
        if (selectedX == -1 && selectedY == -1) {
            // Selecting a piece
            if (chessBoard[y][x] != null) {
                selectedX = x;
                selectedY = y;
            }
        } else {

            //moving the piece!

            MovingPiece piece = (MovingPiece) chessBoard[selectedY][selectedX];
            PieceType type = piece.getPieceType();
            chessGame.move(piece, y, x);



            //chessGame.checkWinCondition();
            //chessGame.matchOutcome();

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
        chessGame.surrender();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
