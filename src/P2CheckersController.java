import game.Board;
import game.GameState;
import game.GameType;
import game.checkers.Checkers;
import game.checkers.CheckersPiece;
import game.pieces.Piece;
import game.pieces.PieceType;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import matchmaking.MatchData;


/**
 * Displays a checkers board and pieces on the board.
 *
 * @author Shakil Hussain and Arwa A, modified by Adam Chan
 */
public class P2CheckersController extends Application implements DataInitializable<MatchData>{

    private static final String ASSETS_PATH = "file:diagrams/gui/assets/sprites/";

    @FXML
    private GameState currentState;
    @FXML
    private GridPane gameBoard;
    @FXML
    private Checkers game = new Checkers();
    @FXML
    private Label p1Label;
    @FXML
    private Label p2Label;
    @FXML
    private StackPane menuButton;
    @FXML
    private StackPane chatButton;
    @FXML
    private AnchorPane rootPane;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/P2Checkers.fxml"));
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


    private void handleMove(int row, int col) {
        Piece[][] boardState = game.getBoard().getBoardState();

        CheckersPiece clickedPiece = (CheckersPiece) boardState[row][col];

        if (game.selectedPiece == null) {
            // first click: select the piece if it belongs to the current player
            if (clickedPiece != null && clickedPiece.getPieceType() == getCurrentPlayer()) {
                game.selectedPiece = clickedPiece;
            }
        } else {
            // second click: move piece to new (clicked) position
            if (clickedPiece == null) {
                CheckersPiece toMove = (CheckersPiece) game.selectedPiece;
                game.move(toMove, row, col); // call move
                game.selectedPiece = null;   // deselect the piece after move

            } else {
                // If the clicked spot is occupied by another piece, deselect the current piece
                System.out.println("Invalid move! Cell is occupied.");
                game.selectedPiece = null; // deselect the piece on invalid move
            }
            updateBoard();
        }


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

    public PieceType getCurrentPlayer() {
        return game.getGameState() == GameState.P1_TURN ? PieceType.DARK : PieceType.LIGHT;
    }


    // edits the null image in gameboard to reflect the piece the player has put down.
    private void updateBoard() {

        Piece[][] board = game.getBoard().getBoardState();

        if (game.getGameState() == GameState.P1_TURN) {
            System.out.println("Player 1 (Purple)'s turn!");
            updatePlayerLabels();
        } else if (game.getGameState() == GameState.P2_TURN){
            System.out.println("Player 2 (Pink)'s turn!");
            updatePlayerLabels();
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ImageView imageView = getNodeByRowColumnIndex(row, col, gameBoard);
                Piece currentPiece = board[row][col];

                if (imageView == null) continue; // prevent null pointer just in case

                if (currentPiece == null) {
                    imageView.setImage(null); // empty cell
                } else {
                    if (currentPiece.getColor().equals(Color.WHITE)) {
                        imageView.setImage(new Image(ASSETS_PATH + "pinkChecker.png"));
                    } else if (currentPiece.getColor().equals(Color.BLACK)) {
                        imageView.setImage(new Image(ASSETS_PATH + "purpleChecker.png"));
                    }

                    // update king pieces to reflect their new role :P
                    if (currentPiece.isKing()) {
                        // Overlay or use a different image if you have one
                        if (currentPiece.getPieceType() == PieceType.LIGHT) {
                            imageView.setImage(new Image(ASSETS_PATH + "pinkKingChecker.png"));
                        } else {
                            imageView.setImage(new Image(ASSETS_PATH + "purpleKingChecker.png"));
                        }
                    }
                }
            }
        }
    }

    // used to find the appropriate node on the gameboard that we need to adjust
    private ImageView getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
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
        if (game.getGameState() == GameState.P2_TURN) {
            p1Label.setOpacity(.5);
            p2Label.setOpacity(1);
        } else if (game.getGameState() == GameState.P1_TURN) {
            p1Label.setOpacity(1);
            p2Label.setOpacity(.5);
        } else {
            p1Label.setOpacity(1);
            p2Label.setOpacity(1);
        }

    }

    public void initialize() {
        Board board = new Board(GameType.CHECKERS);
        game.setBoard(board);
        for (Node node : gameBoard.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            int r = row == null ? 0 : row;
            int c = col == null ? 0 : col;

            node.setOnMouseClicked(e -> handleMove(r, c));
        }

        game.start(); // push game out of setup mode
        updateBoard();
        UtilityManager.createScaleTransition(menuButton);
        UtilityManager.createScaleTransition(chatButton);
    }

    @FXML
    public void goToPopup(javafx.scene.input.MouseEvent mouseEvent) {
        UtilityManager.popupOpen(mouseEvent, "screens/MenuPopup.fxml", rootPane);
    }

    @FXML
    public void goToChat() {
        UtilityManager.chatControl();
    }

    private void handleOfferDraw() {
        System.out.println("Draw offer sent!");
    }

    private void handleResign() {
        game.surrender();
    }


    public static void main(String[] args) {
        launch(args);
    }

}

