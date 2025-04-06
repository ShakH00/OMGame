package networkingObjectsSending;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class PlayerClient extends Application {

    private static final int SHIFT_AMOUNT = 100;
    private int width = 200, height = 600;

    private TextArea message, textGridMessage, testText;
    private Button startb00;
    private Button b01, b02, b03, b04, b05, b06, b07, b08, b09;

    private ClientSideConnection csc;
    private int playerID;
    private int otherPlayerID;
    private int[] values;
    private char[][] server2dChar;
    private int maxTurns;
    private int turnsMade;
    private int myPoints;
    private int enemyPoints;
    private boolean buttonsEnabled;
    private boolean gameIsActive;

    private PracticeGameObj practiceGameObj;

    private Button sendchat;
    private HashMap<Integer, String> chatLogs;

    @Override
    public void start(Stage primaryStage) {
        playerMenu(primaryStage);
    }



    public void playerMenu(Stage primaryStage) {
        // CHAT GTP TRANSLATED THIS FROM SWING TO JAVAFX
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);
        primaryStage.setTitle("The Game Menu");
        chatLogs = new HashMap<>();

        practiceGameObj = new PracticeGameObj(false, new char[2][2], "test");
        // Initialize arrays and variables
        values = new int[4];
        server2dChar = new char[3][3];
        turnsMade = 0;
        myPoints = 0;
        enemyPoints = 0;
        gameIsActive = false;
        buttonsEnabled = false;

        VBox menuLayout = new VBox();
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setSpacing(10);

        testText = new TextArea("lol");
        testText.setEditable(false);



        // "Start Game" button
        startb00 = new Button("Start Matchmaking");
        startb00.setOnAction(e -> {
            System.out.println("YOU PRESSED THE BUTTON");

            connectToServer();   // Connect to the server
            setUpGameScene(primaryStage);
        });

        menuLayout.getChildren().addAll(startb00, testText);

        Scene menuScene = new Scene(menuLayout, width, height);
        primaryStage.setScene(menuScene);

        // 🔹 Add dynamic window shifting on each run
        Random random = new Random();
        double screenWidth = javafx.stage.Screen.getPrimary().getVisualBounds().getWidth();
        int SHIFT_AMOUNT = 130; // Pixels to shift per instance
        int newX = (random.nextInt(5) * SHIFT_AMOUNT) % (int) (screenWidth - width);
        int newY = 100; // Fixed Y-position

        primaryStage.setX(newX);
        primaryStage.setY(newY);

        primaryStage.show();

        // end of CHAT GTP
    }

    private void setUpLoadingScreen(Stage primaryStage) {
        gameIsActive = true;
        primaryStage.setTitle("MATCHMAKING LOADING...");

        // Main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // "MATCHMAKING LOADING..." Message
        Label loadingMessage = new Label("MATCHMAKING LOADING...");

        // Add elements to layout
        root.getChildren().add(loadingMessage);

        // Scene setup
        Scene loadingScene = new Scene(root, width, height);
        primaryStage.setScene(loadingScene);
        primaryStage.show();
    }

    private void setUpGameScene(Stage primaryStage) {
        // CHAT GTP TRANSLTED THIS FROM SWING TO JAVAFX
        gameIsActive = true;
        primaryStage.setTitle("The Game for Player #" + playerID);
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
        message = new TextArea("Turn-based game");
        message.setWrapText(true);
        message.setEditable(false);
        message.setMaxHeight(50);
        root.getChildren().add(message);
        textGridMessage = new TextArea();
        textGridMessage.setWrapText(true);
        textGridMessage.setEditable(false);
        textGridMessage.setMaxHeight(50);
        root.getChildren().add(textGridMessage);
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.setAlignment(Pos.CENTER);

        b01 = new Button("1");
        b02 = new Button("2");
        b03 = new Button("3");
        b04 = new Button("4");
        b05 = new Button("5");
        b06 = new Button("6");
        b07 = new Button("7");
        b08 = new Button("8");
        b09 = new Button("9");
        buttonGrid.addRow(0, b01, b02, b03);
        buttonGrid.addRow(1, b04, b05, b06);
        buttonGrid.addRow(2, b07, b08, b09);
        setUpGameButtons();
        root.getChildren().add(buttonGrid);
        Scene gameScene = new Scene(root, width, height);
        primaryStage.setScene(gameScene);
        primaryStage.show();
        // END of CHAT GTP

        // === Chat UI ===
        Label chatLabel = new Label("Chat with opponent:");
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.setPrefHeight(150);

        HBox chatInputBox = new HBox(10);
        chatInputBox.setAlignment(Pos.CENTER);
        chatInputBox.setPadding(new Insets(10));

        TextField chatInput = new TextField();
        chatInput.setPromptText("Type your message...");
        chatInput.setPrefWidth(300);

        sendchat = new Button("Send");

        sendchat.setOnAction(e -> {
            String msg = chatInput.getText().trim();
            if (!msg.isEmpty()) {
                String formatted = "player" + playerID + ": " + msg + "\n";
                chatArea.appendText(formatted);
                // Log message under current player ID
                String currentLog = chatLogs.get(playerID);
                chatLogs.put(playerID, currentLog + formatted);

                // (Later: send to server)
                System.out.println("Log for player " + playerID + ":");
                System.out.println(chatLogs.get(playerID));

                chatInput.clear();
            }
        });


        chatInputBox.getChildren().addAll(chatInput, sendchat);

// 🔥 Add chat components once (in order)
        root.getChildren().addAll(chatLabel, chatArea, chatInputBox);
        
        
        if (playerID == 1) {
            message.setText("You are player 1, you go first");
            otherPlayerID = 2;
            buttonsEnabled = true;
        } else {
            message.setText("You are player 2, wait for your turn");
            otherPlayerID = 1;
            buttonsEnabled = false;
            // Start waiting for the opponent in a separate thread
            Thread t = new Thread(this::updateTurn);
            t.start();
        }
        toggleButtons();
    }


    /**
     * Sets the onAction for each of the 9 game buttons.
     */
    private void setUpGameButtons() {
        b01.setOnAction(e -> handleButtonClick("1"));
        b02.setOnAction(e -> handleButtonClick("2"));
        b03.setOnAction(e -> handleButtonClick("3"));
        b04.setOnAction(e -> handleButtonClick("4"));
        b05.setOnAction(e -> handleButtonClick("5"));
        b06.setOnAction(e -> handleButtonClick("6"));
        b07.setOnAction(e -> handleButtonClick("7"));
        b08.setOnAction(e -> handleButtonClick("8"));
        b09.setOnAction(e -> handleButtonClick("9"));

    }

    /**
     * Called when a button (1-9) is clicked
     */
    private void hnadleB00Click(){

    }

    private void handleButtonClick(String strBNum) {
        practiceGameObj.setTestString(strBNum);
        message.setText("You clicked button #" + practiceGameObj.getTestString() + " now wait for next player's turn");
        textGridMessage.setText(practiceGameObj.getBoard().toString());

        turnsMade++;
        System.out.println("Turns made: " + turnsMade);

        buttonsEnabled = false;
        toggleButtons();

        // Send button to server
        if (csc != null) {
            csc.sendPracticeGameObj();
        }

        // If P2 hits max turns, check winner
        if (playerID == 2 && turnsMade == maxTurns) {
            checkWinner();
        } else {
            // Otherwise wait for the opponent
            new Thread(this::updateTurn).start();
        }
    }

    /**
     * Enables or disables the 9 game buttons based on 'buttonsEnabled'.
     */
    private void toggleButtons() {
        b01.setDisable(!buttonsEnabled);
        b02.setDisable(!buttonsEnabled);
        b03.setDisable(!buttonsEnabled);
        b04.setDisable(!buttonsEnabled);
        b05.setDisable(!buttonsEnabled);
        b06.setDisable(!buttonsEnabled);
        b07.setDisable(!buttonsEnabled);
        b08.setDisable(!buttonsEnabled);
        b09.setDisable(!buttonsEnabled);
    }

    /**
     * Connect to the server
     */
    public void connectToServer() {
        csc = new ClientSideConnection();

    }

    /**
     * Wait for the opponent's move
     */
    public void updateTurn() {

        csc.receivePracticeGameObj();
        message.setText("your opponent clicked #" + practiceGameObj.getTestString() + "now your Turn");
        textGridMessage.setText(String.valueOf(practiceGameObj.getBoard()[0][0]));

        // If P1 hits max turns, check winner
        if (playerID == 1 && turnsMade == maxTurns) {
            checkWinner();
        } else {
            buttonsEnabled = true;
        }
        toggleButtons();
    }

    /**
     * Checks who won or if it's a tie
     */
    private void checkWinner() {
        buttonsEnabled = false;
        // Compare 'myPoints' vs 'enemyPoints' if you actually tracked them
        // Right now it's just a placeholder
        if (myPoints > enemyPoints) {
            message.setText("You won! You: " + myPoints + " points, Enemy: " + enemyPoints + " points");
        } else if (myPoints < enemyPoints) {
            message.setText("You lost! You: " + myPoints + " points, Enemy: " + enemyPoints + " points");
        } else {
            message.setText("It's a tie! Both have " + myPoints + " points.");
        }
        csc.closeConnection();
    }

    /**
     * Converts server2dChar to a human-readable string
     */
    public String server2dCharToString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : server2dChar) {
            sb.append("[");
            for (char cell : row) {
                sb.append("['").append(cell).append("'], ");
            }
            sb.setLength(sb.length() - 2); // remove trailing ", "
            sb.append("]\n");
        }
        return sb.toString();
    }

    /**
     * The networking client side
     */
    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        private ObjectOutputStream objectDataOut;
        private ObjectInputStream objectDataIn;

        public ClientSideConnection() {
            System.out.println("Client side connection");
            try {
                socket = new Socket("localhost", 30000);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                objectDataOut = new ObjectOutputStream(dataOut);
                objectDataIn = new ObjectInputStream(dataIn);

                playerID = dataIn.readInt();
                System.out.println("Player ID: " + playerID);


            } catch (IOException e) {
                System.out.println("IO exception from CSC constructor");
            }
        }

        public void sendPracticeGameObj(){
            try {
                objectDataOut.writeObject(practiceGameObj);
                objectDataOut.flush();
            } catch (IOException e) {
                System.out.println("Error sending practice game obj: ");
            }
        }

        public void receivePracticeGameObj(){
            try {
                Object tempObj = objectDataIn.readObject(); // vague object gets "catched first_
                practiceGameObj = (PracticeGameObj) tempObj;
            } catch (IOException e){
                System.out.println("Error receiving practice game obj: ");
            } catch (ClassNotFoundException e) {
                System.out.println("object class not found");
            }
        }




        public void closeConnection() {
            try {
                socket.close();
                System.out.println("Closing connection");
            } catch (IOException e) {
                System.out.println("IO exception in ClientSideConnection closeConnection");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}