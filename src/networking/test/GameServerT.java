package networking.test;

import game.tictactoe.TicTacToe_Logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


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
    private HashMap<Integer, String> chatlogs;


    // store  the button num that the player clicked on, befroe being sent to the other player
    // don in the run method while loop, for each turns
    private String player1ButtonNum;
    private String player2ButtonNum;


    //GAME LOGIC UHHH
    private TicTacToe_Logic ticTacToeGame = new TicTacToe_Logic();

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


    private boolean isValidMove(String move, int playerID) {
        return true;
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
                TicTacToe_Logic game = new TicTacToe_Logic();

                while (true) {
                    if(playerID == 1){
                        player1ButtonNum = String.valueOf(dataIn.readChar());  // Reads one char and converts to String // read it from player 1
                        System.out.println("Payer 1 clicked button #" + player1ButtonNum);
                        // Update array
                        //processGameLogicP1(player1ButtonNum);
                        processGameLogic(1,player1ButtonNum);
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
                        //processGameLogicP2(player2ButtonNum);
                        processGameLogic(2,player2ButtonNum);


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

        public void processMove(String input, int playerID) {
            try {
                int move = Integer.parseInt(input) - 1;
                int row = move / 3;
                int col = move % 3;

                char expectedSymbol = (playerID == 1) ? 'X' : 'O';

                char[][] boardBefore = ticTacToeGame.getBoard();
                if (boardBefore[row][col] == '-') {
                    // Apply move
                    ticTacToeGame.getBoard()[row][col] = expectedSymbol;

                    // Print move info
                    System.out.println("Player " + playerID + " placed '" + expectedSymbol + "' at [" + row + "][" + col + "]");
                } else {
                    System.out.println("Invalid move by Player " + playerID + " at [" + row + "][" + col + "]");
                }

            } catch (Exception e) {
                System.out.println("Error processing move for player " + playerID + ": " + e.getMessage());
            }
        }

        public void processGameLogic(int playerNum, String input){

            if(playerNum == 1 ){
                ticTacToeGame.play(playerNum, input);
                server2dChar = ticTacToeGame.getBoard();
            }else {
                ticTacToeGame.play(playerNum, input);
                server2dChar = ticTacToeGame.getBoard();
            }

        }
            // I ASKED chatgtp to give be a better fromat instead of 2 sets fo 9 if statments
        public void processGameLogicP1(String input) {
            placeMove(input, 'X');
        }
        public void processGameLogicP2(String input2) {
                placeMove(input2, 'O');  // P2 uses 'O'
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

        public void sendChatMessage(String message) throws IOException {
            dataOut.writeUTF("CHAT:" + message);
            dataOut.flush();
        }
    }




    public static void main(String[] args) {
        GameServerT gs = new GameServerT();
        gs.acceptConnections();

    }
}
