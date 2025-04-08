package game.chess;

import game.Board;
import game.GameState;
import game.GameType;
import game.chess.Chess;
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

    private int selectedRow = -1;
    private int selectedCol = -1;


    // i know this is crazy. but god it WONT WORK OTHERWISE AJHBDHBFUYHWEF
    @FXML
    private void handleCellClick00(javafx.scene.input.MouseEvent event) { handleMove(0, 0); }

    @FXML
    private void handleCellClick01(javafx.scene.input.MouseEvent event) { handleMove(0, 1); }

    @FXML
    private void handleCellClick02(javafx.scene.input.MouseEvent event) { handleMove(0, 2); }

    @FXML
    private void handleCellClick03(javafx.scene.input.MouseEvent event) { handleMove(0, 3); }

    @FXML
    private void handleCellClick04(javafx.scene.input.MouseEvent event) { handleMove(0, 4); }

    @FXML
    private void handleCellClick05(javafx.scene.input.MouseEvent event) { handleMove(0, 5); }

    @FXML
    private void handleCellClick06(javafx.scene.input.MouseEvent event) { handleMove(0, 6); }

    @FXML
    private void handleCellClick07(javafx.scene.input.MouseEvent event) { handleMove(0, 7); }

    @FXML
    private void handleCellClick10(javafx.scene.input.MouseEvent event) { handleMove(1, 0); }

    @FXML
    private void handleCellClick11(javafx.scene.input.MouseEvent event) { handleMove(1, 1); }

    @FXML
    private void handleCellClick12(javafx.scene.input.MouseEvent event) { handleMove(1, 2); }

    @FXML
    private void handleCellClick13(javafx.scene.input.MouseEvent event) { handleMove(1, 3); }

    @FXML
    private void handleCellClick14(javafx.scene.input.MouseEvent event) { handleMove(1, 4); }

    @FXML
    private void handleCellClick15(javafx.scene.input.MouseEvent event) { handleMove(1, 5); }

    @FXML
    private void handleCellClick16(javafx.scene.input.MouseEvent event) { handleMove(1, 6); }

    @FXML
    private void handleCellClick17(javafx.scene.input.MouseEvent event) { handleMove(1, 7); }

    @FXML
    private void handleCellClick20(javafx.scene.input.MouseEvent event) { handleMove(2, 0); }

    @FXML
    private void handleCellClick21(javafx.scene.input.MouseEvent event) { handleMove(2, 1); }

    @FXML
    private void handleCellClick22(javafx.scene.input.MouseEvent event) { handleMove(2, 2); }

    @FXML
    private void handleCellClick23(javafx.scene.input.MouseEvent event) { handleMove(2, 3); }

    @FXML
    private void handleCellClick24(javafx.scene.input.MouseEvent event) { handleMove(2, 4); }

    @FXML
    private void handleCellClick25(javafx.scene.input.MouseEvent event) { handleMove(2, 5); }

    @FXML
    private void handleCellClick26(javafx.scene.input.MouseEvent event) { handleMove(2, 6); }

    @FXML
    private void handleCellClick27(javafx.scene.input.MouseEvent event) { handleMove(2, 7); }

    @FXML
    private void handleCellClick30(javafx.scene.input.MouseEvent event) { handleMove(3, 0); }

    @FXML
    private void handleCellClick31(javafx.scene.input.MouseEvent event) { handleMove(3, 1); }

    @FXML
    private void handleCellClick32(javafx.scene.input.MouseEvent event) { handleMove(3, 2); }

    @FXML
    private void handleCellClick33(javafx.scene.input.MouseEvent event) { handleMove(3, 3); }

    @FXML
    private void handleCellClick34(javafx.scene.input.MouseEvent event) { handleMove(3, 4); }

    @FXML
    private void handleCellClick35(javafx.scene.input.MouseEvent event) { handleMove(3, 5); }

    @FXML
    private void handleCellClick36(javafx.scene.input.MouseEvent event) { handleMove(3, 6); }

    @FXML
    private void handleCellClick37(javafx.scene.input.MouseEvent event) { handleMove(3, 7); }

    @FXML
    private void handleCellClick40(javafx.scene.input.MouseEvent event) { handleMove(4, 0); }

    @FXML
    private void handleCellClick41(javafx.scene.input.MouseEvent event) { handleMove(4, 1); }

    @FXML
    private void handleCellClick42(javafx.scene.input.MouseEvent event) { handleMove(4, 2); }

    @FXML
    private void handleCellClick43(javafx.scene.input.MouseEvent event) { handleMove(4, 3); }

    @FXML
    private void handleCellClick44(javafx.scene.input.MouseEvent event) { handleMove(4, 4); }

    @FXML
    private void handleCellClick45(javafx.scene.input.MouseEvent event) { handleMove(4, 5); }

    @FXML
    private void handleCellClick46(javafx.scene.input.MouseEvent event) { handleMove(4, 6); }

    @FXML
    private void handleCellClick47(javafx.scene.input.MouseEvent event) { handleMove(4, 7); }

    @FXML
    private void handleCellClick50(javafx.scene.input.MouseEvent event) { handleMove(5, 0); }

    @FXML
    private void handleCellClick51(javafx.scene.input.MouseEvent event) { handleMove(5, 1); }

    @FXML
    private void handleCellClick52(javafx.scene.input.MouseEvent event) { handleMove(5, 2); }

    @FXML
    private void handleCellClick53(javafx.scene.input.MouseEvent event) { handleMove(5, 3); }

    @FXML
    private void handleCellClick54(javafx.scene.input.MouseEvent event) { handleMove(5, 4); }

    @FXML
    private void handleCellClick55(javafx.scene.input.MouseEvent event) { handleMove(5, 5); }

    @FXML
    private void handleCellClick56(javafx.scene.input.MouseEvent event) { handleMove(5, 6); }

    @FXML
    private void handleCellClick57(javafx.scene.input.MouseEvent event) { handleMove(5, 7); }

    @FXML
    private void handleCellClick60(javafx.scene.input.MouseEvent event) { handleMove(6, 0); }

    @FXML
    private void handleCellClick61(javafx.scene.input.MouseEvent event) { handleMove(6, 1); }

    @FXML
    private void handleCellClick62(javafx.scene.input.MouseEvent event) { handleMove(6, 2); }

    @FXML
    private void handleCellClick63(javafx.scene.input.MouseEvent event) { handleMove(6, 3); }

    @FXML
    private void handleCellClick64(javafx.scene.input.MouseEvent event) { handleMove(6, 4); }

    @FXML
    private void handleCellClick65(javafx.scene.input.MouseEvent event) { handleMove(6, 5); }

    @FXML
    private void handleCellClick66(javafx.scene.input.MouseEvent event) { handleMove(6, 6); }

    @FXML
    private void handleCellClick67(javafx.scene.input.MouseEvent event) { handleMove(6, 7); }

    @FXML
    private void handleCellClick70(javafx.scene.input.MouseEvent event) { handleMove(7, 0); }

    @FXML
    private void handleCellClick71(javafx.scene.input.MouseEvent event) { handleMove(7, 1); }

    @FXML
    private void handleCellClick72(javafx.scene.input.MouseEvent event) { handleMove(7, 2); }

    @FXML
    private void handleCellClick73(javafx.scene.input.MouseEvent event) { handleMove(7, 3); }

    @FXML
    private void handleCellClick74(javafx.scene.input.MouseEvent event) { handleMove(7, 4); }

    @FXML
    private void handleCellClick75(javafx.scene.input.MouseEvent event) { handleMove(7, 5); }

    @FXML
    private void handleCellClick76(javafx.scene.input.MouseEvent event) { handleMove(7, 6); }

    @FXML
    private void handleCellClick77(javafx.scene.input.MouseEvent event) { handleMove(7, 7); }


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/Chess.fxml"));
            Scene scene = new Scene(loader.load(), 800, 570);
////
////            String fontPath = getClass().getResource("resources/fonts/PressStart2P-Regular.ttf").toExternalForm();
////            String retroGamingPath = getClass().getResource("resources/fonts/RetroGaming.ttf").toExternalForm();
////            String pixelitePath = getClass().getResource("resources/fonts/Pixelite.ttf").toExternalForm();
////
////            Font pressStartFont = Font.loadFont(fontPath, 40);
////            Font retroGamingFont = Font.loadFont(retroGamingPath, 40);
////            Font pixeliteFont = Font.loadFont(pixelitePath, 40);
//
//            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
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
                    ((Pawn) selectedPiece).promote(4, game.getBoard());
                }
            }


            // clear selection either way
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


    public static void main(String[] args) {
        launch(args);
    }

}

