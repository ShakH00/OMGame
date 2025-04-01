/**
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
        
        drawStylishBackground(gc);
        drawTitle(gc);
        drawBoard(gc);
        
        canvas.setOnMouseClicked(e -> handleClick(e, gc));
        
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        primaryStage.setTitle("Connect 4 - Visual Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void drawStylishBackground(GraphicsContext gc) {
        // Diagonal gradient background
        LinearGradient gradient = new LinearGradient(
            0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.DARKBLUE),
            new Stop(0.5, Color.PURPLE),
            new Stop(1, Color.DARKRED)
        );
        gc.setFill(gradient);
        gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // Subtle grid pattern
        gc.setStroke(Color.rgb(255, 255, 255, 0.1));
        for (int x = 0; x < WINDOW_WIDTH; x += 30) {
            gc.strokeLine(x, 0, x, WINDOW_HEIGHT);
        }
    }

    private void drawTitle(GraphicsContext gc) {
        gc.setFont(Font.font("Impact", 36));
        gc.setFill(Color.WHITE);
        gc.fillText("CONNECT 4", WINDOW_WIDTH/2 - 90, 50);
        
        gc.setFont(Font.font("Arial", 14));
        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Click columns to place pieces", WINDOW_WIDTH/2 - 90, 70);
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
        
        // Slot shadow
        gc.setFill(Color.rgb(0, 0, 0, 0.3));
        gc.fillOval(x + 2, y + 2, CIRCLE_SIZE, CIRCLE_SIZE);
        
        if (color == null) {
            // Empty slot (glass effect)
            LinearGradient slotGradient = new LinearGradient(
                0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(255, 255, 255, 0.7)),
                new Stop(1, Color.rgb(220, 220, 255, 0.3))
            );
            gc.setFill(slotGradient);
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
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        drawStylishBackground(gc);
        drawTitle(gc);
        drawBoard(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}