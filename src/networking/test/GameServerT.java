/* package networking.test;

import game.Game;

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
    private ServerSocket ss;
    private int numPlayers;

    private ServerSideConnection player1;
    private ServerSideConnection player2;

    private int[] values;
    private char[][] server2dChar;

    //GAME LOGIC UHHH
    private Game cachedGame;

    public GameServerT() {
        System.out.println("--Game Server--");
        numPlayers = 0;
        disconnectedPlayers = new HashMap<>();
        try{
            ss = new ServerSocket(30000);
        } catch(IOException e){
            System.out.println("IOException from GameServer constructor");
            e.printStackTrace();
        }
    }

    public void acceptConnections(){
        try {
            System.out.println("waiting for connections");
            while (numPlayers < 2) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Player #" + numPlayers + " has joined the game.");
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                if (numPlayers == 1) {
                    player1 = ssc;
                } else {
                    player2 = ssc;
                }
                Thread t = new Thread(ssc); //what ever is the in the ssc run in the new "THREAD"
                t.start();
            }
            System.out.println("2 players reached, no longer looking for players.");
        }catch(IOException e){
            System.out.println("IOException: acceptConnections");
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
                dataOut.flush();

                while (true) {
                    if(playerID == 1){
                        player1ButtonNum = String.valueOf(dataIn.readChar());  // Reads one char and converts to String // read it from player 1
                        System.out.println("Player 1 clicked button #" + player1ButtonNum);
                        for (char[] row : server2dChar) {
                            System.out.println(Arrays.toString(row));
                        }
                        player2.sendButtonNum(player1ButtonNum);
                        player2.send2dCharArray(); // sending server2dChar

                    }
                    else{
                        player2ButtonNum = String.valueOf(dataIn.readChar());
                        System.out.println("Player 2 clicked button #" + player2ButtonNum);
                        System.out.println("input before p2" + player2ButtonNum);
                        for (char[] row : server2dChar) {
                            System.out.println(Arrays.toString(row));
                        }
                        player1.sendButtonNum(player2ButtonNum);
                        player1.send2dCharArray();
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
}*/