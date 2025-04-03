/**
 * Connect 4 revamped gui
 * 
 * - Amr Ibrahim :) (tried matching design edition)
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Connect4Controller extends Application {

    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final int CIRCLE_SIZE = 60;
    private static final int SPACING = 8;
    private static final int BOARD_OFFSET_X = 80;
    private static final int BOARD_OFFSET_Y = 150;
    private static final int WINDOW_WIDTH = 650;
    private static final int WINDOW_HEIGHT = 750;

    private Color[][] board = new Color[ROWS][COLUMNS];
    private boolean isBlackTurn = true;
    private Cloud[] clouds = new Cloud[5];

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Initialize clouds
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud(
                Math.random() * WINDOW_WIDTH,
                Math.random() * 100,
                50 + Math.random() * 70,
                30 + Math.random() * 50
            );
        }
        
        drawBackground(gc);
        drawBoard(gc);
        
        canvas.setOnMouseClicked(e -> handleClick(e, gc));
        
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        primaryStage.setTitle("Retro Connect 4 - Outline Slots");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void drawBackground(GraphicsContext gc) {
        // Dark blue retro sky
        gc.setFill(Color.rgb(20, 30, 80));
        gc.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // Draw stars
        gc.setFill(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            double x = Math.random() * WINDOW_WIDTH;
            double y = Math.random() * BOARD_OFFSET_Y;
            double size = 1 + Math.random() * 2;
            gc.fillOval(x, y, size, size);
        }
        
        // Draw clouds
        gc.setFill(Color.rgb(220, 220, 255, 0.8));
        for (Cloud cloud : clouds) {
            drawCloud(gc, cloud.x, cloud.y, cloud.width, cloud.height);
        }
        
        // Retro title
        gc.setFont(Font.font("Impact", 48));
        gc.setFill(Color.YELLOW);
        gc.fillText("CONNECT 4", WINDOW_WIDTH/2 - 140, 80);
    }

    private void drawCloud(GraphicsContext gc, double x, double y, double w, double h) {
        gc.fillOval(x, y + h/4, w, h);
        gc.fillOval(x + w/3, y, w, h);
        gc.fillOval(x + w*2/3, y + h/4, w, h);
    }

    private void drawBoard(GraphicsContext gc) {
        // Dark yellow board border (retro style)
        gc.setFill(Color.rgb(180, 150, 30));
        gc.fillRoundRect(
            BOARD_OFFSET_X - 15, BOARD_OFFSET_Y - 15,
            COLUMNS * (CIRCLE_SIZE + SPACING) + 30,
            ROWS * (CIRCLE_SIZE + SPACING) + 30, 25, 25
        );
        
        // Board interior matches sky background
        gc.setFill(Color.rgb(20, 30, 80));
        gc.fillRoundRect(
            BOARD_OFFSET_X, BOARD_OFFSET_Y,
            COLUMNS * (CIRCLE_SIZE + SPACING),
            ROWS * (CIRCLE_SIZE + SPACING), 15, 15
        );

        // Draw slot outlines (empty circles)
        gc.setStroke(Color.rgb(220, 220, 255, 0.5));
        gc.setLineWidth(2);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                double x = BOARD_OFFSET_X + col * (CIRCLE_SIZE + SPACING);
                double y = BOARD_OFFSET_Y + row * (CIRCLE_SIZE + SPACING);
                gc.strokeOval(x, y, CIRCLE_SIZE, CIRCLE_SIZE);
            }
        }

        // Draw placed pieces
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] != null) {
                    drawPiece(gc, col, row, board[row][col]);
                }
            }
        }
    }

    private void drawPiece(GraphicsContext gc, int col, int row, Color color) {
        double x = BOARD_OFFSET_X + col * (CIRCLE_SIZE + SPACING);
        double y = BOARD_OFFSET_Y + row * (CIRCLE_SIZE + SPACING);
        
        // Solid piece
        gc.setFill(color);
        gc.fillOval(x + 2, y + 2, CIRCLE_SIZE - 4, CIRCLE_SIZE - 4);
        
        // Piece outline
        gc.setStroke(color == Color.BLACK ? Color.WHITE : Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeOval(x + 2, y + 2, CIRCLE_SIZE - 4, CIRCLE_SIZE - 4);
    }

    private void handleClick(MouseEvent e, GraphicsContext gc) {
        int col = (int) ((e.getX() - BOARD_OFFSET_X) / (CIRCLE_SIZE + SPACING));
        
        if (col >= 0 && col < COLUMNS) {
            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] == null) {
                    board[row][col] = isBlackTurn ? Color.BLACK : Color.WHITE;
                    isBlackTurn = !isBlackTurn;
                    redraw(gc);
                    break;
                }
            }
        }
    }

    private void redraw(GraphicsContext gc) {
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        drawBackground(gc);
        drawBoard(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class Cloud {
        double x, y, width, height;
        
        Cloud(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
}