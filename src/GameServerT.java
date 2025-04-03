import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameServerT {
    private Map<Integer, ServerSideConnection> disconnectedPlayers;
    private boolean gameInProgress;
    private ServerSocket ss;
    private int numPlayers;
    private ServerSideConnection player1;
    // these a the server side equilivant to the package drop off pouints i think
    private ServerSideConnection player2;
    private int turnsMade;
    private int maxTurns;
    private int[] values;
    private char[][] server2dChar;

    // store the  the button num that the player clicked on, befroe being sent to the other player
    // don in the run method while loop, for each turns
    private String player1ButtonNum;
    private String player2ButtonNum;

    private char[] gameBoard;

    public GameServerT() {
        System.out.println("--game server--");
        numPlayers = 0;
        turnsMade = 0;
        maxTurns = 90;
        values = new int[4];
        server2dChar = new char[3][3];
        disconnectedPlayers = new HashMap<>();
        gameInProgress = false;

        for (int i = 0; i < 4; i++) { //Ading the values fromt he server not
            values[i] = i;
        }

        //
        for (int i = 0; i < server2dChar.length; i++) {
            for (int j = 0; j < server2dChar[i].length; j++) {
                server2dChar[i][j] = ' ';
            }
        }

        for (int i = 0; i < server2dChar.length; i++) {
            for (int j = 0; j < server2dChar[i].length; j++) {
                System.out.print("["+ server2dChar[i][j] + "]");
            }
            System.out.println();
        }

        try{
            ss = new ServerSocket(30000);
        } catch(IOException e){
            System.out.println("IOException from game server constructor");
            e.printStackTrace();
        }
    }

    public void acceptConnections(){

        try {
            System.out.println("waiting for connections");

            while (numPlayers < 2) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("webSocketNotes.Player #" + numPlayers + " has joined the game");
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                if (numPlayers == 1) {
                    player1 = ssc;
                } else {
                    player2 = ssc;
                }

                Thread t = new Thread(ssc); //what ever is the in the ssc run in the new "THREAD"
                t.start();
            }
            System.out.println("2 player reach, no more looking for players");
        }catch(IOException e){
            System.out.println("IOException from game server acceptConnections");
        }
    }

    public void handleDisconnection(int playerID) {
        if (playerID == 1) {
            disconnectedPlayers.put(playerID, player1);
            player1 = null;
        } else if (playerID == 2) {
            disconnectedPlayers.put(playerID, player2);
            player2 = null;
        }
        broadcastMessage("Player " + playerID + " disconnected");
    }

    public boolean handleReconnection(Socket socket, int playerID) {
        try {
            ServerSideConnection newConnection = new ServerSideConnection(socket, playerID);
            if (playerID == 1) {
                player1 = newConnection;
            } else if (playerID == 2) {
                player2 = newConnection;
            }
            disconnectedPlayers.remove(playerID);
            new Thread(newConnection).start();
            sendGameState(playerID);
            return true;
        } catch (Exception e) {
            System.err.println("Reconnection failed: " + e.getMessage());
            return false;
        }
    }

    public void broadcastMessage(String message) {
        try {
            if (player1 != null) {
                player1.sendChatMessage(message);
            }
            if (player2 != null) {
                player2.sendChatMessage(message);
            }
        } catch (IOException e) {
            System.out.println("IOException in broadcastMessage: " + e.getMessage());
        }
    }

    public void sendGameState(int playerID) {
        ServerSideConnection player = (playerID == 1) ? player1 : player2;
        if (player != null) {
            player.send2dCharArray();
        }
    }

    private boolean isValidMove(String move, int playerID) {
        try {
            String[] coords = move.split(",");
            int row = Integer.parseInt(coords[0]);
            int col = Integer.parseInt(coords[1]);
            
            // Check if within bounds
            if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                return false;
            }
            
            // Check if space is empty
            if (server2dChar[row][col] != ' ') {
                return false;
            }
            
            // Check if it's player's turn
            boolean isPlayer1Turn = turnsMade % 2 == 0;
            return (playerID == 1 && isPlayer1Turn) || (playerID == 2 && !isPlayer1Turn);
            
        } catch (Exception e) {
            return false;
        }
    }

    private class ServerSideConnection implements Runnable{
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int playerID;


        public ServerSideConnection(Socket s, int id){
            socket = s;
            playerID = id;

            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("IOException from game server constructor: ServerSideConnection");
            }
        }
        public void run(){ // insctruction we want to run on a NEW thread
            try {
                dataOut.writeInt(playerID);
                dataOut.writeInt(maxTurns);

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        dataOut.writeChar(server2dChar[i][j]);
                    }
                }
                dataOut.flush();


                while (true) {
                    if(playerID == 1){
                        player1ButtonNum = String.valueOf(dataIn.readChar());  // Reads one char and converts to String // read it from player 1
                        System.out.println("Payer 1 clicked button #" + player1ButtonNum);
                        // Update array
                        processGameLogicP1(player1ButtonNum);
                        for (char[] row : server2dChar) {
                            System.out.println(Arrays.toString(row));
                        }
                        player2.sendButtonNum(player1ButtonNum);
                        player2.send2dCharArray(); // sending server2dChar

                    }
                    else{
                        player2ButtonNum = String.valueOf(dataIn.readChar());
                        System.out.println("Payer 2 clicked button #" + player2ButtonNum);
                        System.out.println("input before p2" + player2ButtonNum);
                        processGameLogicP2(player2ButtonNum);

                        for (char[] row : server2dChar) {
                            System.out.println(Arrays.toString(row));
                        }
                        player1.sendButtonNum(player2ButtonNum);
                        player1.send2dCharArray();
                    }
                    turnsMade++;

                    if(endGame()){
                        System.out.println("Max turns reached");
                        break; // break from the game and end
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException from run() : ServerSideConnection");
            }

            try {
                while (true) {
                    String input = dataIn.readUTF();
                    if (input.startsWith("CHAT:")) {
                        handleChatMessage(input.substring(5)); // Remove "CHAT:" prefix
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException from run() : ServerSideConnection");
            }
        }


        public void sendButtonNum(String buttonNum){
            try{
                dataOut.writeChars(buttonNum);
                dataOut.flush();
            } catch (IOException e) {
                System.out.println("IOException from sendButtonNum() : ServerSideConnection");
            }
        }

        public void send2dCharArray() {
            try {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        dataOut.writeChar(server2dChar[i][j]);
                    }
                }
                dataOut.flush();
            } catch (IOException e) {
                System.out.println("IOException from send2dCharArray() : ServerSideConnection");
            }
        }

        public boolean endGame(){
            return turnsMade >= maxTurns;
        }

            // I ASKED chatgtp to give be a better fromat instead of 2 sets fo 9 if statments
        public void processGameLogicP1(String input) {
            if (isValidMove(input, 1)) {
                placeMove(input, 'X');  // P1 uses 'X'
                if (checkWin('X')) {
                    System.out.println("Player 1 wins!");
                    broadcastMessage("Player 1 wins!");
                    gameInProgress = false;
                } else if (checkDraw()) {
                    System.out.println("It's a draw!");
                    broadcastMessage("It's a draw!");
                    gameInProgress = false;
                }
            } 
            else {
                System.out.println("Invalid move by Player 1: " + input);
            }
        }

        public void processGameLogicP2(String input2) {
            if (isValidMove(input2, 2)) {
                placeMove(input2, 'O');  // P2 uses 'O'
                if (checkWin('O')) {
                    System.out.println("Player 2 wins!");
                    broadcastMessage("Player 2 wins!");
                    gameInProgress = false;
                } else if (checkDraw()) {
                    System.out.println("It's a draw!");
                    broadcastMessage("It's a draw!");
                    gameInProgress = false;
                }
            }
            else {
                System.out.println("Invalid move by Player 2: " + input2);
            } 
        }

        private void placeMove(String input, char symbol) {


            //  networking -> Server game logic -> networking
            if (symbol == 'O'){
                System.out.println("processGameLogic for player 2, input: " + input);
            }
            else {
                System.out.println("processGameLogic for player 1, input: " + input);
            }

            int move = Integer.parseInt(input) - 1; // Convert input to index (0-based)
            int row = move / 3;
            int col = move % 3;

            if (server2dChar[row][col] == ' ') { // Check if the cell is empty
                server2dChar[row][col] = symbol;
            } else {
                System.out.println("Invalid move! Cell already occupied.");
            }
        }
        // END of chatgtp tentious work

        private boolean checkWin(char symbol) {
            // Check rows and columns
            for (int i = 0; i < 3; i++) {
                if ((server2dChar[i][0] == symbol && server2dChar[i][1] == symbol && server2dChar[i][2] == symbol) || 
                    (server2dChar[0][i] == symbol && server2dChar[1][i] == symbol && server2dChar[2][i] == symbol)) {
                    return true;
                }
            }
            // Check diagonals
            if ((server2dChar[0][0] == symbol && server2dChar[1][1] == symbol && server2dChar[2][2] == symbol) || 
                (server2dChar[0][2] == symbol && server2dChar[1][1] == symbol && server2dChar[2][0] == symbol)) {
                return true;
            }
            return false;
        }

        private boolean checkDraw() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (server2dChar[i][j] == ' ') {
                        return false; // Empty space found, not a draw
                    }
                }
            }
            return true; // No empty spaces, it's a draw
        }

        public void sendChatMessage(String message) throws IOException {
            dataOut.writeUTF("CHAT:" + message);
            dataOut.flush();
        }

        public void handleChatMessage(String message) {
            try {
                String formattedMessage = "CHAT:" + message;
                if (player1 != null) {
                    player1.sendChatMessage(formattedMessage);
                }
                if (player2 != null) {
                    player2.sendChatMessage(formattedMessage);
                }
            } catch (IOException e) {
                System.out.println("Error broadcasting chat message: " + e.getMessage());
            }
        }
    }




    public static void main(String[] args) {
        GameServerT gs = new GameServerT();
        gs.acceptConnections();

    }
}

// STUB – Handles invalid or unexpected input from a player.
public void handleInvalidInput(String input, int playerID) {
    System.out.println("⚠️ Invalid input received from Player " + playerID + ": " + input);
    // TODO: Trigger UI warning or log error
}


// STUB – Sends an error message to a client.
public void notifyClientOfError(int playerID, String message) {
    System.out.println("🔔 Notifying Player " + playerID + " of error: " + message);
    // TODO: Add network message sending logic here
}


// STUB – Logs and prepares recovery steps for player disconnection.
public void logDisconnection(int playerID) {
    System.out.println("⚠️ Player " + playerID + " disconnected unexpectedly.");
    // TODO: Pause game session and notify opponent
}
