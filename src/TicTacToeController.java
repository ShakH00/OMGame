import game.GameState;
import game.pieces.Piece;
import game.pieces.PieceType;
import game.tictactoe.TicTacToe;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class TicTacToeController extends Application {

    private static final String ASSETS_PATH = "file:diagrams/gui/assets/sprites/";

    @FXML
    private GameState currentState;
    @FXML
    private Label p1Label;
    @FXML
    private Label p2Label;
    @FXML
    private GridPane gameBoard;
    @FXML
    private TicTacToe game = new TicTacToe();
    @FXML
    private StackPane menuButton;
    @FXML
    private StackPane chatButton;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/TicTacToe.fxml"));

            Scene scene = new Scene(loader.load(), 800, 570);

              // TODO: idk if these will work while this controller is in tictactoe and not with her friends
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
    private void handleCellClick0(MouseEvent event) { handleMove(0, 0); }

    @FXML
    private void handleCellClick1(MouseEvent event) { handleMove(0, 1); }

    @FXML
    private void handleCellClick2(MouseEvent event) { handleMove(0, 2); }

    @FXML
    private void handleCellClick3(MouseEvent event) { handleMove(1, 0); }

    @FXML
    private void handleCellClick4(MouseEvent event) { handleMove(1, 1); }

    @FXML
    private void handleCellClick5(MouseEvent event) { handleMove(1, 2); }

    @FXML
    private void handleCellClick6(MouseEvent event) { handleMove(2, 0); }

    @FXML
    private void handleCellClick7(MouseEvent event) { handleMove(2, 1); }

    @FXML
    private void handleCellClick8(MouseEvent event) { handleMove(2, 2); }



    // adds a piece to the gameboard, checks for win conditions, and switches turns.
    private void handleMove(int row, int col) {
        if (currentState == GameState.P1_WIN || currentState == GameState.P2_WIN || currentState == GameState.DRAW) {
            return; // game over
        }

        updatePlayerLabels();

        // who's turn is it?
        Piece currentPiece = game.getGameState() == GameState.P1_TURN ? game.piece1 : game.piece2;
        Piece[][] board = game.getBoard().getBoardState();

        // check if the cell is already occupied
        if (board[row][col] != null) {
            // if it's already occupied, return early to prevent a move
            System.out.println("Cell is already occupied! Try a different one.");
            return;
        }


        game.move(currentPiece, row, col); // make a move
        updateBoard(); // update the board to reflect the move
        game.nextTurn(); // switch to the next player's turn



        // check game state
        currentState = game.getGameState();

        if (currentState == GameState.P1_WIN) {
            System.out.println("Player 1 wins!");
        } else if (currentState == GameState.P2_WIN) {
            System.out.println("Player 2 wins!");
        } else if (currentState == GameState.DRAW) {
            System.out.println("It’s a draw!");
        }
    }

    // update the player labels based on the pieces (and make the active player's name stand out!)
    private void updatePlayerLabels() {
        if (game.piece1.getPieceType() == PieceType.LIGHT) {
            // player 1 is X (blue)
            p1Label.setStyle("-fx-text-fill: #42b0da;");
            p2Label.setStyle("-fx-text-fill: #fe8fb7;");

            if (currentState == GameState.P1_TURN || currentState == GameState.SETUP) {
                p1Label.setOpacity(.5);
                p2Label.setOpacity(1);
            } else if (currentState == GameState.P2_TURN) {
                p1Label.setOpacity(1);
                p2Label.setOpacity(.5);
            } else {
                p1Label.setOpacity(1);
                p2Label.setOpacity(1);
            }

        } else {
            // player 1 is O (pink)
            p1Label.setStyle("-fx-text-fill: #fe8fb7;");
            p2Label.setStyle("-fx-text-fill: #42b0da;");

            if (currentState == GameState.P1_TURN) {
                p1Label.setOpacity(0.5);
                p2Label.setOpacity(1);
            } else if (currentState == GameState.P2_TURN) {
                p1Label.setOpacity(1);
                p2Label.setOpacity(0.5);
            } else {
                p1Label.setOpacity(1);
                p2Label.setOpacity(1);
            }

        }
    }

    // edits the null image in gameboard to reflect the piece the player has put down.
    private void updateBoard() {
        Piece[][] board = game.getBoard().getBoardState();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                ImageView imageView = getNodeByRowColumnIndex(row, col, gameBoard);
                Piece currentPiece = board[row][col];

                if (currentPiece == null) {
                    imageView.setImage(null); // empty cell
                } else {
                    if (currentPiece.getPieceType().equals(PieceType.LIGHT)) {
                        imageView.setImage(new Image(ASSETS_PATH + "X.png"));
                    } else if (currentPiece.getPieceType().equals(PieceType.DARK)) {
                        imageView.setImage(new Image(ASSETS_PATH + "O.png"));
                    }
                }
            }
        }
    }

    // used to find the appropriate node on the gameboard that we need to adjust
    private ImageView getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(node);
            Integer c = GridPane.getColumnIndex(node);
            int actualRow = (r == null) ? 0 : r;
            int actualCol = (c == null) ? 0 : c;

            if (actualRow == row && actualCol == column) {
                return (ImageView) node;
            }
        }
        return null;
    }


    public void initialize() {
        game.start(); // push game out of setup mode
        updateBoard(); // Ensure the board is updated once everything is initialized
        StartController.createScaleTransition(menuButton);
        StartController.createScaleTransition(chatButton);
    }

    @FXML
    public void goToPopup() {
        UtilityManager.popupControl();
    }

    @FXML
    public void goToChat() {
        UtilityManager.chatControl();
    }

    public static void main(String[] args) {
        launch(args);
    }
}