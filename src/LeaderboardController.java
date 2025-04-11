import javafx.scene.Node;
import javafx.stage.Stage;
import leaderboard.Leaderboard;



import account.Account;
import account.NoAccountError;
import account.statistics.StatisticType;
import game.GameType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    @FXML
    private Button tictactoeButton;

    @FXML
    private Button chessButton;

    @FXML
    private Button checkersButton;

    @FXML
    private Button backButton;

    @FXML
    private ToggleButton globalToggle;

    @FXML
    private ToggleButton friendsToggle;

    @FXML
    private ToggleGroup leaderboardType;

    @FXML
    private ComboBox<StatisticType> sortStatComboBox;

    @FXML
    private ComboBox<StatisticType> additionalStatComboBox;

    @FXML
    private ToggleButton ascendingToggle;


    @FXML
    private TableView<LeaderboardEntry> leaderboardTable;

    @FXML
    private TableColumn<LeaderboardEntry, String> rankColumn;

    @FXML
    private TableColumn<LeaderboardEntry, String> usernameColumn;

    @FXML
    private TableColumn<LeaderboardEntry, String> eloColumn;

    @FXML
    private TableColumn<LeaderboardEntry, String> winRateColumn;

    @FXML
    private TableColumn<LeaderboardEntry, String> winsColumn;

    @FXML
    private TableColumn<LeaderboardEntry, String> additionalStatColumn;

    @FXML
    private Button connect4Button;



    private Leaderboard leaderboard;
    private GameType currentGameType;
    private Account currentAccount;
    private StatisticType currentSortStat = StatisticType.ELO;
    private StatisticType currentAdditionalStat = StatisticType.WIN_RATE;
    private boolean isAscending = false;
    private int entriesPerPage = 10;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leaderboard = new Leaderboard();


//        currentAccount = null;


        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        eloColumn.setCellValueFactory(new PropertyValueFactory<>("elo"));
        winRateColumn.setCellValueFactory(new PropertyValueFactory<>("winRate"));
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        additionalStatColumn.setCellValueFactory(new PropertyValueFactory<>("additionalStat"));


        sortStatComboBox.getItems().addAll(StatisticType.values());
        additionalStatComboBox.getItems().addAll(StatisticType.values());

        // Set default values
        sortStatComboBox.setValue(currentSortStat);
        additionalStatComboBox.setValue(currentAdditionalStat);
    }

    @FXML
    private void onGameButtonClicked(ActionEvent event) {
        if (event.getSource() == tictactoeButton) {
            currentGameType = GameType.TICTACTOE;
        } else if (event.getSource() == chessButton) {
            currentGameType = GameType.CHESS;
        } else if (event.getSource() == checkersButton) {
            currentGameType = GameType.CHECKERS;
        } else if (event.getSource() == connect4Button) {
            currentGameType = GameType.CONNECT4;
        }

        loadLeaderboard();
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(stage, "/screens/Start.fxml");
    }

    @FXML
    private void onLeaderboardTypeToggled(ActionEvent event) {

        loadLeaderboard();
    }

    @FXML
    private void onSortStatChanged(ActionEvent event) {
        currentSortStat = sortStatComboBox.getValue();
        loadLeaderboard();
    }

    @FXML
    private void onAdditionalStatChanged(ActionEvent event) {
        StatisticType selected = additionalStatComboBox.getValue();


        if (selected == StatisticType.ELO) {
            showAlert("ELO is already displayed in a dedicated column. Please select a different statistic.");
            additionalStatComboBox.setValue(currentAdditionalStat);
            return;
        }

        currentAdditionalStat = selected;
        loadLeaderboard();


        additionalStatColumn.setText(currentAdditionalStat.toString());
    }

    @FXML
    private void onSortOrderToggled(ActionEvent event) {
        isAscending = ascendingToggle.isSelected();
        loadLeaderboard();
    }

    private void loadLeaderboard() {
        if (currentGameType == null) {
            return;
        }

        String[][] leaderboardData;

        try {
            if (globalToggle.isSelected()) {
                leaderboardData = leaderboard.getGlobalLeaderboard(
                        currentGameType,
                        currentSortStat,
                        currentAdditionalStat,
                        isAscending,
                        Integer.MAX_VALUE,  // Show ALL entries
                        1                   // Single page
                );
            } else {
                if (currentAccount == null || currentAccount.getIsGuest()) {
                    showAlert("You must be logged in to view friends leaderboard.");
                    globalToggle.setSelected(true);
                    return;
                }

                leaderboardData = leaderboard.getFriendsLeaderboard(
                        currentAccount,
                        currentGameType,
                        currentSortStat,
                        currentAdditionalStat,
                        isAscending,
                        Integer.MAX_VALUE,
                        1
                );
            }

            ObservableList<LeaderboardEntry> entries = FXCollections.observableArrayList();

            for (int i = 1; i < leaderboardData.length; i++) {
                String[] row = leaderboardData[i];

                if (row[0].isEmpty()) continue;

                entries.add(new LeaderboardEntry(
                        row[0], row[1], row[2], row[3], row[4], row[5]
                ));
            }

            leaderboardTable.setItems(entries);
            additionalStatColumn.setText(currentAdditionalStat.toString());

        } catch (NoAccountError e) {
            showAlert("You must be logged in to view friends leaderboard.");
            globalToggle.setSelected(true);
        } catch (Exception e) {
            showAlert("Error loading leaderboard: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void loadLeaderboardPage(int pageNumber) {
        if (currentGameType == null) {
            return;
        }

        String[][] leaderboardData;

        try {

            if (globalToggle.isSelected()) {
                leaderboardData = leaderboard.getGlobalLeaderboard(
                        currentGameType,
                        currentSortStat,
                        currentAdditionalStat,
                        isAscending,
                        -1,
                        1
                );
            } else {

                if (currentAccount == null || currentAccount.getIsGuest()) {
                    showAlert("You must be logged in to view friends leaderboard.");
                    globalToggle.setSelected(true);
                    return;
                }

                leaderboardData = leaderboard.getFriendsLeaderboard(
                        currentAccount,
                        currentGameType,
                        currentSortStat,
                        currentAdditionalStat,
                        isAscending,
                        entriesPerPage,
                        pageNumber
                );
            }


            ObservableList<LeaderboardEntry> entries = FXCollections.observableArrayList();


            for (int i = 1; i < leaderboardData.length; i++) {
                String[] row = leaderboardData[i];

                // Skip empty rows
                if (row[0].isEmpty()) {
                    continue;
                }

                LeaderboardEntry entry = new LeaderboardEntry(
                        row[0], // rank
                        row[1], // username
                        row[2], // elo
                        row[3], // win rate
                        row[4], // wins
                        row[5]  // additional stat
                );
                entries.add(entry);
            }


            leaderboardTable.setItems(entries);


            additionalStatColumn.setText(currentAdditionalStat.toString());

        } catch (NoAccountError e) {
            showAlert("You must be logged in to view friends leaderboard.");
            globalToggle.setSelected(true);
        } catch (Exception e) {
            showAlert("Error loading leaderboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Leaderboard");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static class LeaderboardEntry {
        private String rank;
        private String username;
        private String elo;
        private String winRate;
        private String wins;
        private String additionalStat;

        public LeaderboardEntry(String rank, String username, String elo, String winRate, String wins, String additionalStat) {
            this.rank = rank;
            this.username = username;
            this.elo = elo;
            this.winRate = winRate;
            this.wins = wins;
            this.additionalStat = additionalStat;
        }

        public String getRank() { return rank; }
        public void setRank(String rank) { this.rank = rank; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getElo() { return elo; }
        public void setElo(String elo) { this.elo = elo; }

        public String getWinRate() { return winRate; }
        public void setWinRate(String winRate) { this.winRate = winRate; }

        public String getWins() { return wins; }
        public void setWins(String wins) { this.wins = wins; }

        public String getAdditionalStat() { return additionalStat; }
        public void setAdditionalStat(String additionalStat) { this.additionalStat = additionalStat; }
    }
}