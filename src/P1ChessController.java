import account.statistics.MatchOutcomeHandler;
import game.Board;
import game.Game;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import networking.Networking;

import java.util.concurrent.TimeUnit;
import matchmaking.MatchData;

import java.util.ArrayList;
import java.util.List;


/**
 * Displays a chess board and pieces on the board.
 *
 * @author Shakil Hussain and Arwa A, modified by Adam Chan
 */
public class P1ChessController extends Application implements DataInitializable<MatchData>{

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

    private networking.Networking networking = new networking.Networking();
    private String selfUsername;
    private String opponentUsername;
    private int selfPlayerNo;
    private int opponentPlayerNo;

    @FXML
    private Pane gameOver;
    @FXML
    private Text playerWonLabel;
    @FXML
    private StackPane mainMenuButton;

    private List<ImageView> moveHighlights = new ArrayList<>();


    private int selectedRow = -1;
    private int selectedCol = -1;
    private MovingPiece promotionPawn; // temporarily store the pawn

    private chess1Watcher watcher;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/P1Chess.fxml"));
            loader.setController(this); // Set the controller to this instance
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
            networking.connectToServer();
            watcher = new chess1Watcher(networking, this);
            watcher.start(); // start the thread to listen for game updates
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleMove(int row, int col) {
        Piece[][] boardState = game.getBoard().getBoardState();
        Piece clicked = boardState[row][col];

        // clear previous highlights from last move
        clearMoveHighlights();

        // no selection yet
        if (selectedRow == -1 && clicked instanceof MovingPiece moving) {
            // check if it's the current player's turn
            PieceType currentTurn = (game.getState() == GameState.P1_TURN) ? PieceType.LIGHT : PieceType.DARK;
            if (moving.getPieceType() == currentTurn) {
                selectedRow = row;
                selectedCol = col;

                // show legal moves
                showLegalMoves(clicked);
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
                networking.sendGame(game);
            updateBoard();
        }

        GameState state = game.getState();
        if (state == GameState.P1_WIN) {
            System.out.println("Player 1 wins!");
            if (selfPlayerNo == 1) {
                playerWonLabel.setText(selfUsername + " won!");
            } else {
                playerWonLabel.setText(opponentUsername + " won!");
            }
            gameOver.setVisible(true);
            MatchOutcomeHandler.RecordMatchOutcome(GameType.CHESS, game.matchOutcomeP1());
        } else if (state == GameState.P2_WIN) {
            System.out.println("Player 2 wins!");
            if (selfPlayerNo == 2) {
                playerWonLabel.setText(selfUsername + " won!");
            } else {
                playerWonLabel.setText(opponentUsername + " won!");
                MatchOutcomeHandler.RecordMatchOutcome(GameType.CHESS, game.matchOutcomeP1());
            }
            gameOver.setVisible(true);
        } else if (state == GameState.DRAW) {
            System.out.println("It’s a draw!");
            playerWonLabel.setText("It's a draw!");
            gameOver.setVisible(true);
            MatchOutcomeHandler.RecordMatchOutcome(GameType.CHESS, game.matchOutcomeP1());
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

    private void showLegalMoves(Piece selectedPiece) {
        // clear previous moves shown
        clearMoveHighlights();

        if (selectedPiece == null) return;

        // get legal moves
        List<int[]> legalMoves = game.getLegalMoves((MovingPiece) selectedPiece);

        for (int[] move : legalMoves) {
            int row = move[0];
            int col = move[1];

            // get the gridpane cell at this position
            ImageView moveMarker = getNodeByRowColumnIndex(row, col, gameBoard);

            if (moveMarker != null) {
                // create a new imageview to overlay on top
                ImageView dotOverlay = new ImageView(new Image(ASSETS_PATH + "moveDot.png"));

                // so it won't intercept clicks
                dotOverlay.setMouseTransparent(true);

                // position the dot over the cell
                dotOverlay.setFitWidth(moveMarker.getFitWidth());
                dotOverlay.setFitHeight(moveMarker.getFitHeight());
                dotOverlay.setPreserveRatio(true);

                // add dot to the board
                GridPane.setRowIndex(dotOverlay, row);
                GridPane.setColumnIndex(dotOverlay, col);
                GridPane.setHalignment(dotOverlay, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(dotOverlay, javafx.geometry.VPos.CENTER);
                gameBoard.getChildren().add(dotOverlay);

                // add dots to moveHighlights to be cleared later
                moveHighlights.add(dotOverlay);
            }
        }
    }

    // clears all move dots after a turn
    private void clearMoveHighlights() {
        // remove highlight dots from board
        gameBoard.getChildren().removeAll(moveHighlights);
        moveHighlights.clear();
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

    private void handleOfferDraw() {
        System.out.println("Draw offer sent!");
    }

    private void handleResign() {
        game.surrender();
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

    public void netUpdate(Game game){
        System.out.println("NetUpdate");
        this.game = (Chess) game;
        System.out.println("Game state updated" );
        javafx.application.Platform.runLater(() -> updateBoard());
    }
}

class chess1Watcher extends Thread {
    private final Networking networking;
    private final P1ChessController gui;
    public chess1Watcher(Networking networking, P1ChessController gui) {
        this.networking = networking;
        this.gui = gui;
    }

    public void run(){
        while (networking.isConnected && networking.shouldListen){  // stop early if they stop matchmaking
            // Check if the handler has found a match. If it has, start the game UI
            if (networking.gameRecieved) {
                System.out.println("Asadasd");
                networking.gameRecieved = false; // Reset the flag after processing
                gui.netUpdate(networking.recieveGame());
            }

            // Sleep 1 sec before repeating
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}