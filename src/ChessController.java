import game.Board;
import game.GameState;
import game.GameType;
import game.chess.*;
import game.pieces.MovingPiece;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Displays a chess board and pieces on the board.
 *
 * @author Shakil Hussain and Arwa A, modified by Adam Chan
 */
public class ChessController extends Application {

    private static final String ASSETS_PATH = "file:diagrams/gui/assets/sprites/";

    @FXML
    private GameState currentState;
    @FXML
    private GridPane gameBoard;
    @FXML
    private Chess game = new Chess();
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

    @FXML
    private Pane pawnPromotion;

    @FXML
    private StackPane knightButton;
    @FXML
    private StackPane bishopButton;
    @FXML
    private StackPane rookButton;
    @FXML
    private StackPane queenButton;


    private int selectedRow = -1;
    private int selectedCol = -1;
    private MovingPiece promotionPawn; // temporarily store the pawn


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/Chess.fxml"));
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
        Piece clicked = boardState[row][col];

        // no selection yet
        if (selectedRow == -1 && clicked instanceof MovingPiece moving) {
            // check if it's the current player's turn
            PieceType currentTurn = (game.getState() == GameState.P1_TURN) ? PieceType.LIGHT : PieceType.DARK;
            if (moving.getPieceType() == currentTurn) {
                selectedRow = row;
                selectedCol = col;
            }
            return;
        }

        // there is a piece already selected
        if (selectedRow != -1) {
            MovingPiece selectedPiece = (MovingPiece) boardState[selectedRow][selectedCol];

            game.move(selectedPiece, row, col);

            if (selectedPiece instanceof Pawn) {
                boolean toPromote = ((Pawn) selectedPiece).checkPromotion();


                if (toPromote) {
                    promotionPawn = selectedPiece; // store the pawn to promote
                    pawnPromotion.setVisible(true); // make promotion popup visible
                    return; // pause until promotion gets chosen
                }
            }


            // clear selection only after a valid move or promotion
                selectedRow = -1;
                selectedCol = -1;
            updateBoard();
        }
    }

    // edits the null image in gameboard to reflect the piece the player has put down.
    private void updateBoard() {

        Piece[][] board = game.getBoard().getBoardState();

        if (game.getState() == GameState.P1_TURN) {
            System.out.println("Player 1 (Pink)'s turn!");
            updatePlayerLabels();
        } else if (game.getState() == GameState.P2_TURN){
            System.out.println("Player 2 (Blue)'s turn!");
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
                    String colorPrefix = currentPiece.getColor().equals(Color.WHITE) ? "pink" : "blue";
                    String pieceName = "";

                    if (currentPiece instanceof Pawn) {
                        pieceName = "Pawn";
                    } else if (currentPiece instanceof Rook) {
                        pieceName = "Rook";
                    } else if (currentPiece instanceof Knight) {
                        pieceName = "Knight";
                    } else if (currentPiece instanceof Bishop) {
                        pieceName = "Bishop";
                    } else if (currentPiece instanceof Queen) {
                        pieceName = "Queen";
                    } else if (currentPiece instanceof King) {
                        pieceName = "King";
                    }

                    String imagePath = ASSETS_PATH + colorPrefix + pieceName + "Chess.png";
                    imageView.setImage(new Image(imagePath));
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
        if (game.getState() == GameState.P2_TURN) {
            p1Label.setOpacity(.5);
            p2Label.setOpacity(1);
        } else if (game.getState() == GameState.P1_TURN) {
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
        //UtilityManager.createScaleTransition(menuButton);
        //UtilityManager.createScaleTransition(chatButton);
    }

    @FXML
    public void goToPopup(javafx.scene.input.MouseEvent mouseEvent) {
        //UtilityManager.popupControl(mouseEvent, "screens/MenuPopup.fxml", rootPane);
    }

    @FXML
    public void goToChat() {
        //UtilityManager.chatControl();
    }

    private void handleOfferDraw() {
        System.out.println("Draw offer sent!");
    }

    private void handleResign() {
        game.surrender();
        game.matchOutcome();
    }

    @FXML
    private void handleKnight() {
        ((Pawn) promotionPawn).promote(1, game.getBoard());
        pawnPromotion.setVisible(false);
        selectedRow = -1;
        selectedCol = -1;
        updateBoard();
    }

    @FXML
    private void handleBishop() {
        ((Pawn) promotionPawn).promote(2, game.getBoard());
        pawnPromotion.setVisible(false);
        selectedRow = -1;
        selectedCol = -1;
        updateBoard();
    }

    @FXML
    private void handleRook() {
        ((Pawn) promotionPawn).promote(3, game.getBoard());
        pawnPromotion.setVisible(false);
        selectedRow = -1;
        selectedCol = -1;
        updateBoard();
    }

    @FXML
    private void handleQueen() {
        ((Pawn) promotionPawn).promote(4, game.getBoard());
        pawnPromotion.setVisible(false);
        selectedRow = -1;
        selectedCol = -1;
        updateBoard();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

