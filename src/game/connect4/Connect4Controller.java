package game.connect4; /**
 * Connect 4 GUI - Visual
 * Click columns to drop pieces (alternating colors)
 * No game logic edition
 * Apologies btw (;﹏;)
 * - Amr Ibrahim
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Connect4Controller extends Application {

    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final int CIRCLE_SIZE = 70;
    private static final int SPACING = 5;
    private static final int BOARD_OFFSET_X = 40;
    private static final int BOARD_OFFSET_Y = 80;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 700;

    private Color[][] board = new Color[ROWS][COLUMNS]; // Track piece colors
    private Color currentPlayerColor = Color.RED; // Start with red

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawBoard(gc);
        
        canvas.setOnMouseClicked(e -> handleClick(e, gc));

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
        
        primaryStage.setTitle("Connect 4 - Visual Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void drawBoard(GraphicsContext gc) {
        // Board frame
        gc.setFill(Color.rgb(50, 50, 60));
        gc.fillRoundRect(
            BOARD_OFFSET_X - 15, BOARD_OFFSET_Y - 15,
            COLUMNS * (CIRCLE_SIZE + SPACING) + 30,
            ROWS * (CIRCLE_SIZE + SPACING) + 30, 20, 20
        );
        
        // Blue playing surface
        gc.setFill(Color.rgb(30, 80, 150, 0.8));
        gc.fillRoundRect(
            BOARD_OFFSET_X, BOARD_OFFSET_Y,
            COLUMNS * (CIRCLE_SIZE + SPACING),
            ROWS * (CIRCLE_SIZE + SPACING), 10, 10
        );

        // Draw all pieces (empty or placed)
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                drawSlot(gc, col, row, board[row][col]);
            }
        }
    }

    private void drawSlot(GraphicsContext gc, int col, int row, Color color) {
        double x = BOARD_OFFSET_X + col * (CIRCLE_SIZE + SPACING);
        double y = BOARD_OFFSET_Y + row * (CIRCLE_SIZE + SPACING);
        
        if (color == null) {
            gc.setFill(Color.rgb(255,255,255));
        } else {
            // Player piece with glossy effect
            RadialGradient pieceGradient = new RadialGradient(
                0, 0, 0.3, 0.3, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0, color.deriveColor(0, 1, 1, 1)),
                new Stop(1, color.deriveColor(0, 1, 0.5, 1))
            );
            gc.setFill(pieceGradient);
        }
        gc.fillOval(x, y, CIRCLE_SIZE, CIRCLE_SIZE);
        
        // Highlight
        gc.setFill(Color.rgb(255, 255, 255, 0.4));
        gc.fillOval(x + 5, y + 5, CIRCLE_SIZE/3, CIRCLE_SIZE/3);
    }

    private void handleClick(MouseEvent e, GraphicsContext gc) {
        int col = (int) ((e.getX() - BOARD_OFFSET_X) / (CIRCLE_SIZE + SPACING));
        
        if (col >= 0 && col < COLUMNS) {
            // Find first empty row in this column (from bottom up)
            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] == null) {
                    board[row][col] = currentPlayerColor;
                    currentPlayerColor = (currentPlayerColor == Color.RED) ? Color.GOLD : Color.RED;
                    redrawBoard(gc);
                    break;
                }
            }
        }
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}