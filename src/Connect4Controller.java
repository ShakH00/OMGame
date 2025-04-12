import account.Account;
import account.LoggedInAccount;
import account.statistics.MatchOutcomeHandler;
import game.GameState;
import game.GameType;
import game.connect4.Connect4;
import game.pieces.Piece;
import javafx.application.Application;
import javafx.css.Match;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import matchmaking.MatchData;


public class Connect4Controller extends Application implements DataInitializable<MatchData> {

    private static final String ASSETS_PATH = "file:diagrams/gui/assets/sprites/";

    @FXML
    private ImageView handImageView;
    @FXML
    private GridPane gameBoard;
    private Connect4 game = new Connect4();
    @FXML
    private StackPane menuButton;
    @FXML
    private StackPane chatButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label p1Label;
    @FXML
    private Label p2Label;

    Account activeAccount;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/Connect4.fxml"));
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

    public void setUserData(int selfID, String selfUsername, int selfElo, String selfNetworkingInfo, int selfPlayerNo,
                            int opponentID, String opponentUsername, int opponentElo, String opponentNetworkingInfo, int opponentPlayerNo,
                            boolean affectsElo) {
        this.selfUsername = selfUsername;
        MatchOutcomeHandler.opponentUsername = opponentUsername;
        this.selfPlayerNo = selfPlayerNo;
        this.opponentPlayerNo = opponentPlayerNo;
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
            updatePlayerLabels();
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

    public void setAccount(Account account) {
        this.activeAccount = account;
    }


        public void initialize() {
            // game.start to push game out of setup mode
            game.start();

            // setup layout for handview animation
            handImageView.setLayoutX(-217); // starting x position
            handImageView.setLayoutY(-23);  // fixed y position
            handImageView.setVisible(false); // set hand to invisible by default
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
        } else if (game.getGameState() == GameState.P1_TURN || game.getGameState() == GameState.SETUP) {
            p1Label.setOpacity(1);
            p2Label.setOpacity(.5);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}