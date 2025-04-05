package game.connect4;
import game.GameState;
import game.pieces.Piece;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
//import resources.fonts.*;


public class Connect4Controller extends Application {

    private static final String ASSETS_PATH = "file:diagrams/gui/assets/sprites/";

    @FXML
    private ImageView handImageView;
    @FXML
    private GridPane gameBoard;
    private Connect4 game = new Connect4();

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game/connect4/Connect4.fxml"));

            Scene scene = new Scene(loader.load(), 800, 570);

            // TODO: these refuse to work..
            String fontPath = getClass().getResource("resources/fonts/PressStart2P-Regular.ttf").toExternalForm();
            Font pressStartFont = Font.loadFont(fontPath, 40);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            primaryStage.setResizable(false);

            // set up the primary stage
            primaryStage.setTitle("OMG!");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleColumnClick0(MouseEvent event) { dropPiece(0); }

    @FXML
    private void handleColumnClick1(MouseEvent event) { dropPiece(1); }

    @FXML
    private void handleColumnClick2(MouseEvent event) { dropPiece(2); }

    @FXML
    private void handleColumnClick3(MouseEvent event) { dropPiece(3); }

    @FXML
    private void handleColumnClick4(MouseEvent event) { dropPiece(4); }

    @FXML
    private void handleColumnClick5(MouseEvent event) { dropPiece(5); }

    @FXML
    private void handleColumnClick6(MouseEvent event) { dropPiece(6); }



    // adds a piece to the gameboard, checks for win conditions, and switches turns.
    public void dropPiece(int column) {

        if (game.getGameState() == GameState.P1_WIN || game.getGameState() == GameState.P2_WIN || game.getGameState() == GameState.DRAW) {
            return; // Game already over
        }

        // Correcting the turn assignment logic here
        Piece currentPiece = game.getGameState() == GameState.P1_TURN ? game.piece1 : game.piece2;


        game.move(currentPiece, column);
        updateBoard();

        GameState state = game.getGameState();
        if (state == GameState.P1_WIN) {
            System.out.println("Player 1 (Pink) wins!");
        } else if (state == GameState.P2_WIN) {
            System.out.println("Player 2 (Blue) wins!");
        } else if (state == GameState.DRAW) {
            System.out.println("It’s a draw!");
        } else {
            game.nextTurn(); // Switch turn
        }
    }


    // edits the null image in gameboard to reflect the piece the player has put down.
    private void updateBoard() {
        Piece[][] state = game.getBoard().getBoardState();
        // loop through board, find the designated cell, and update the image
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                ImageView imageView = getNodeByRowColumnIndex(row, col, gameBoard);
                Piece piece = state[row][col];
                if (piece == null) {
                    imageView.setImage(null); // empty cell
                } else {

                    // TODO: need to adjust this to accept any color (green, blue, pink, purple) the player chooses
                    if (piece.getColor().equals(Color.RED)) {
                        imageView.setImage(new Image(ASSETS_PATH + "pinkChecker.png"));
                    //    System.out.println("Piece color: " + piece.getColor());
                    } else if (piece.getColor().equals(Color.GOLD)) {
                        imageView.setImage(new Image(ASSETS_PATH + "blueChecker.png"));
                    //    System.out.println("Piece color: " + piece.getColor());
                    }
                }
            }
        }
    }



    // used to find the appropriate node on the gameboard that we need to adjust
    public ImageView getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(node);
            Integer c = GridPane.getColumnIndex(node);

            // used to catch null rows/cols to avoid exceptions
            // no idea how this fixed it . . but it didn't work before it
            // sets null rows/cols to 0 by default
            int actualRow = (r == null) ? 0 : r;
            int actualCol = (c == null) ? 0 : c;

            // if actualRow and actualCol match existing rows/columns, no issues!!!
            if (actualRow == row && actualCol == column) {
                return (ImageView) node;
            }
        }
        return null;
    }


        public void initialize() {
            // game.start to push game out of setup mode
            game.start();

            // setup layout for handview animation
            handImageView.setLayoutX(-217); // starting x position
            handImageView.setLayoutY(-23);  // fixed y position
            handImageView.setVisible(false); // set hand to invisible by default
        }



        // method used to make tetriscat hand appear when hovering over columns !
        // this is so silly . . might delete
        @FXML
        private void handleMouseEntered(MouseEvent event) {
            // get the cell that triggered event to find the column #
            ImageView source = (ImageView) event.getSource();
            String id = source.getId();

            // get column index (the last character)
            int col = id.charAt(id.length() - 1) - '0';

            // calculate X position
            double baseX = -217; // starting X for column 0 and 1
            double offset = 60;  // distance between columns

            // col 0 and 1 use the same x, unfortunately, or the hand looks even weirder
            if (col == 0 || col == 1) {
                baseX = -217;
                col = 1;
            }

            col = col - 1;

            // calculate the updated x by multiplying offset by col #
            double newX = baseX + col * offset;

            handImageView.setLayoutX(newX);
            handImageView.setLayoutY(-23);
            handImageView.setVisible(true);

            // if the game ends, immediately set hand to invisible (no more hovering!)
            GameState currentState = game.getGameState();
            if (currentState == GameState.P1_WIN ||
                    currentState == GameState.P2_WIN ||
                    currentState == GameState.DRAW) {
                handImageView.setVisible(false);
            }
        }


        // makes paw invisible when the user stops hovering over a column
        @FXML
        private void handleMouseExited(MouseEvent event) {
            handImageView.setVisible(false);
        }
    public static void main(String[] args) {
        launch(args);
    }
}