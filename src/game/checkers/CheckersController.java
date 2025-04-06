package game.checkers;

import game.Board;
import game.GameType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Displays a checkers board and pieces on the board.
 *
 * @author Shakil Hussain, modified by Adam Chan
 */
public class CheckersController extends Application {

    private static final int TILE_SIZE = 64;
    private static final int BOARD_SIZE = 8;
    private static final String ASSETS_PATH = "file:diagrams/gui/assets/sprites/";

    /*
    * new board to replace old string set. Used in alot of logic
    * */
    private Board board;
    private Checkers checkersGame;

    private int selectedX = -1, selectedY = -1;

    /**
     * Setting up the checkers board UI
     *
     * @author Shakil Hussain, modified by Adam Chan
     */
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        /*
        * Initalizing new board
        * */
        board = new Board(GameType.CHECKERS);
        checkersGame = new Checkers(); // Initialize Checkers game logic
        checkersGame.setBoard(board); // Set the board in the checkers class.
        checkersGame.start();

        drawCheckersBoard(gc);
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

        primaryStage.setTitle("Checkers Board");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Draws the checkers board on the canvas
     *
     * @param gc GraphicsContext used for drawing
     *
     * @author Shakil Hussain
     */
    private void drawCheckersBoard(GraphicsContext gc) {
        boolean white = true;
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                gc.setFill(white ? javafx.scene.paint.Color.rgb(235, 235, 208) : javafx.scene.paint.Color.rgb(119, 148, 85));
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                white = !white;
            }
            white = !white;
        }
    }

    /**
     * Draws the checkers pieces, Modified from original to fit with Checkers game logic
     *
     * @param gc GraphicsContext used for drawing images
     *
     * @author Shakil Hussain, modified by Adam Chan
     */
    private void drawPieces(GraphicsContext gc) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (board.getBoardState()[y][x] != null) {
                    String pieceName = board.getBoardState()[y][x].getPieceType().toString().toLowerCase();
                    if(board.getBoardState()[y][x].getColor().equals(javafx.scene.paint.Color.BLACK)){
                        pieceName = "greenChecker";
                    }
                    else if(board.getBoardState()[y][x].getColor().equals(javafx.scene.paint.Color.WHITE)){
                        pieceName = "orangeChecker";
                    }

                    if (board.getBoardState()[y][x].isKing()){
                        pieceName += "King";
                    }
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
     * @author Shakil Hussain, modified by Adam Chan
     */
    private void handleMouseClick(MouseEvent event, GraphicsContext gc) {
        int x = (int) (event.getX() / TILE_SIZE);
        int y = (int) (event.getY() / TILE_SIZE);

        if (selectedX == -1 && selectedY == -1) {
            // Selecting a piece
            if (board.getBoardState()[y][x] != null) {
                selectedX = x;
                selectedY = y;
            }
        } else {
            // Moving the selected piece using checkers logic
            if(checkersGame.isValidMove(selectedY, selectedX, y, x, board)){
                checkersGame.move((CheckersPiece) board.getBoardState()[selectedY][selectedX], y, x);
            }
            checkersGame.checkWinCondition();
            checkersGame.matchOutcome();

            selectedX = -1;
            selectedY = -1;
        }
        drawCheckersBoard(gc);
        drawPieces(gc);
    }

    private void handleOfferDraw() {
        // Handle draw offer logic here, e.g., show a message or ask for confirmation
        System.out.println("Draw offer sent!");
    }

    private void handleResign() {
        // Handle resignation logic here, e.g., end the game or show a resignation message
        checkersGame.surrender();
        checkersGame.matchOutcome();
    }

    public static void main(String[] args) {
        launch(args);
    }
}