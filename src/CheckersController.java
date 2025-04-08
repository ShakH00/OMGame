import game.Board;
import game.checkers.Checkers;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
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


import java.util.Objects;

public class CheckersController extends Application {

    private static final int TILE_SIZE = 64;
    private static final int BOARD_SIZE = 8;
    private static final String ASSETS_PATH = "file:/diagrams/gui/assets/sprites/";

    /*
     * new board to replace old string set. Used in alot of logic
     * */
    private Board board;
    private Checkers checkersGame;

    private int selectedX = -1, selectedY = -1;

    private final ImageView[][] cells = new ImageView[BOARD_SIZE][BOARD_SIZE];

    // Load images for pieces
    private final Image greenChecker = new Image(Objects.requireNonNull(getClass().getResource("/diagrams/gui/assets/sprites/greenChecker.png")).toString());
    private final Image orangeChecker = new Image(Objects.requireNonNull(getClass().getResource("/diagrams/gui/assets/sprites/orangeChecker.png")).toString());


    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Canvas canvas = new Canvas(TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        /*
         * Initalizing new board
         * */
        board = new Board(GameType.CHECKERS);
        checkersGame = new Checkers(); // Initialize Checkers game logic

        System.out.println("Waiting for opponent's move...");

// Stack Overflow Reference: https://stackoverflow.com/questions/1760654/how-to-call-a-method-at-the-start-of-a-javafx-application
// Receiving game state from opponent before gameplay begins
        networking.Networking networking = new networking.Networking();
        game.Game receivedGame = networking.recieveGame();
        if (receivedGame instanceof Checkers) {
            checkersGame = (Checkers) receivedGame;
        }

        drawCheckersBoard(gc);

//        canvas.setOnMouseClicked(event -> handleMouseClick(event, gc));

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
        GridPane grid = new GridPane();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                StackPane cell = new StackPane();

                // Alternate square color
                Color color = (row + col) % 2 == 0 ? Color.BURLYWOOD : Color.WHITE;
                Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE, color);

                ImageView piece = new ImageView();
                piece.setFitWidth(TILE_SIZE * 0.8);
                piece.setFitHeight(TILE_SIZE * 0.8);
                piece.setPreserveRatio(true);

                cells[row][col] = piece;

                // Add pieces only on dark squares
                if ((row + col) % 2 != 0) {
                    if (row < 3) {
                        piece.setImage(greenChecker);
                    } else if (row > 4) {
                        piece.setImage(orangeChecker);
                    }
                }

                cell.getChildren().addAll(square, piece);
                grid.add(cell, col, row);
            }
        }
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
