import game.Game;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import matchmaking.MatchData;

import static javafx.scene.text.TextAlignment.RIGHT;


public class TicTacToeController extends Application implements DataInitializable<MatchData> {

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
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Pane gameOver;
    @FXML
    private Text playerWonLabel;
    @FXML
    private StackPane mainMenuButton;


    private String selfUsername;
    private String opponentUsername;
    private int selfPlayerNo;
    private int opponentPlayerNo;

    @Override
    public void initializeData(MatchData data) {
        // now we SHOULD be able to get info from matchData
        this.selfUsername = data.getSelfUsername();
        this.opponentUsername = data.getOpponentUsername();
        this.selfPlayerNo = data.getSelfPlayerNo();
        this.opponentPlayerNo = data.getOpponentPlayerNo();

        updatePlayerLabels();

    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/TicTacToe.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);

            String fontPath = getClass().getResource("resources/fonts/PressStart2P-Regular.ttf").toExternalForm();
            String retroGamingPath = getClass().getResource("resources/fonts/RetroGaming.ttf").toExternalForm();
            String pixelitePath = getClass().getResource("resources/fonts/Pixelite.ttf").toExternalForm();

            Font pressStartFont = Font.loadFont(fontPath, 40);
            Font retroGamingFont = Font.loadFont(retroGamingPath, 40);
            Font pixeliteFont = Font.loadFont(pixelitePath, 40);

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
            if (selfPlayerNo == 1) {
                playerWonLabel.setText(selfUsername + " won!");
            } else {
                playerWonLabel.setText(opponentUsername + " won!");
            }
            gameOver.setVisible(true);
        } else if (currentState == GameState.P2_WIN) {
            System.out.println("Player 2 wins!");
            if (selfPlayerNo == 2) {
                playerWonLabel.setText(selfUsername + " won!");
            } else {
                playerWonLabel.setText(opponentUsername + " won!");
            }
            gameOver.setVisible(true);
        } else if (currentState == GameState.DRAW) {
            System.out.println("It’s a draw!");
            playerWonLabel.setText("It's a draw!");
            gameOver.setVisible(true);
        }
    }

    // update the player labels based on the pieces (and make the active player's name stand out!)
    private void updatePlayerLabels() {

        if (selfPlayerNo == 1) {
            // you are p1, and opponent is p2
            p1Label.setText(selfUsername);
            p2Label.setText(opponentUsername);

        } else {
            // otherwise, you are p2, and opponent is p1
            p1Label.setText(opponentUsername);
            p2Label.setText(selfUsername);
        }

        if (game.piece1.getPieceType() == PieceType.LIGHT) {
            // player 1 is X (blue)
            p1Label.setStyle("-fx-text-fill: #42b0da;");
            p2Label.setStyle("-fx-text-fill: #fe8fb7;");
            p2Label.setTextAlignment(RIGHT);

            if (game.getGameState() == GameState.P2_TURN) {
                p1Label.setOpacity(1);
                p2Label.setOpacity(.5);
            } else if (game.getGameState() == GameState.P1_TURN || currentState == GameState.SETUP) {
                p1Label.setOpacity(.5);
                p2Label.setOpacity(1);
            }

        } else {
            // player 1 is O (pink)
            p1Label.setStyle("-fx-text-fill: #fe8fb7;");
            p2Label.setStyle("-fx-text-fill: #42b0da;");
            p2Label.setTextAlignment(RIGHT);

            if (currentState == GameState.P1_TURN || currentState == GameState.SETUP) {
                p1Label.setOpacity(0.5);
                p2Label.setOpacity(1);
            } else if (currentState == GameState.P2_TURN) {
                p1Label.setOpacity(1);
                p2Label.setOpacity(0.5);
            }

        }
    }

    // edits the null image in gameboard to reflect the piece the player has put down.
    private void updateBoard() {
        Piece[][] board = game.getBoard().getBoardState();

        if (game.getGameState() == GameState.P1_TURN) {
            System.out.println("Player 1's turn!");
            updatePlayerLabels();
        } else if (game.getGameState() == GameState.P2_TURN){
            System.out.println("Player 2's turn!");
            updatePlayerLabels();
        }

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
        updatePlayerLabels();
        updateBoard(); // Ensure the board is updated once everything is initialized
        UtilityManager.createScaleTransition(menuButton);
        UtilityManager.createScaleTransition(chatButton);
        UtilityManager.createScaleTransition(mainMenuButton);
    }

    @FXML
    public void goToPopup(javafx.scene.input.MouseEvent mouseEvent) {
        UtilityManager.popupOpen(mouseEvent, "screens/MenuPopup.fxml", rootPane);
    }


    public void goToMainMenu(javafx.scene.input.MouseEvent mouseEvent) {
        UtilityManager.popupOpen(mouseEvent, "screens/MatchType.fxml", rootPane);
    }
    @FXML
    public void goToChat() {
        UtilityManager.chatControl();
    }

    public static void main(String[] args) {
        launch(args);
    }
}