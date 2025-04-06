package networkingObjectsSending;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class GameServer {
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
    private PracticeGameObj practiceGameObj;
    private char[] gameBoard;
    private HashMap<Integer, String> chatLogs;



    public GameServer() {
        System.out.println("--game server--");

        chatLogs = new HashMap<>();

        numPlayers = 0;
        turnsMade = 0;

        practiceGameObj = new PracticeGameObj(false, new char[2][2], "test");

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
                System.out.println("Player #" + numPlayers + " has joined the game");
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



    private class ServerSideConnection implements Runnable{
        private Socket socket;
        private DataOutputStream dataOut;
        private DataInputStream dataIn;
        //private DataInputStream dataInChat;
        //private DataOutputStream dataOutChat;

        private ObjectOutputStream objectOut;
        private ObjectInputStream objectIn;
        private int playerID;


        public ServerSideConnection(Socket s, int id){
            socket = s;
            playerID = id;

            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectIn = new ObjectInputStream(socket.getInputStream());


            } catch (IOException e) {
                System.out.println("IOException from game server constructor: ServerSideConnection");
            }
        }
        public void run(){ // insctruction we want to run on a NEW thread
            try {
                System.out.println("sent player ID: " + playerID);
                dataOut.writeInt(playerID);

                /*new Thread(() -> {
                    handleChatThread();
                }).start();*/

                while (true) {
                    if(playerID == 1){
                        practiceGameObj = (PracticeGameObj) objectIn.readObject();  // Reads one char and converts to String // read it from player 1
                        player1ButtonNum = practiceGameObj.getTestString();
                        System.out.println("Payer 1 clicked button #" + player1ButtonNum);
                        // Update array
                        processGameLogic(1);
                        player2.sendPracticeGameObj(); // sending server2dChar

                    }
                    else{
                        practiceGameObj = (PracticeGameObj) objectIn.readObject();
                        player2ButtonNum = practiceGameObj.getTestString();
                        System.out.println("PLayer 2 clicked button #" + player2ButtonNum);

                        processGameLogic(2);

                        player1.sendPracticeGameObj();
                    }
                    turnsMade++;

                    if(false){
                        System.out.println("GAME HAS ENDED");
                        break; // break from the game and end
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException from run() : ServerSideConnection");
            } catch (ClassNotFoundException e) {
                System.out.println("ClassNotFoundException from run() : ServerSideConnection");
            }
        }
        public void sendPracticeGameObj(){
            try {
                objectOut.writeObject(practiceGameObj);
                objectOut.flush();
            } catch (IOException e){
                System.out.println("IOException from game server sendPracticeGameObj");
            }
        }

       /* public void handleChatThread(){
            System.out.println(chatLogs.toString());
            receiveChats();

        }*/

        public String censorChat(String message){
            return message;
        }

        /*public void receiveChats() {
            try {
                while (true) {
                    Message mesage = (Message) objectIn.readObject();
                    String msg = mesage.getMessage();
                    PlayerNum =
                    msg = censorChat(msg);
                    chatLogs.put(playerNum, msg);
                    System.out.println("chat Player #" + playerNum + ": " + msg);
                        if (playerID == 1 && player2 != null) {
                            player2.sendChatMessage();
                        } else if (playerID == 2 && player1 != null) {
                            player1.sendChatMessage();
                        }

                }
            } catch (IOException e) {
                System.out.println("Chat thread crashed for Player " + playerID);
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        public void sendChatMessage(){
            try {
                dataOut.
            }
        }*/


        public void processGameLogic(int playerID){
            //practiceGameObj = changes made
            // GAME LOGIC TEAM
            //-----------------
            //-----------------
            //-----------------

        }


        public void sendButtonNum(String buttonNum){
            try{
                dataOut.writeChars(buttonNum);
                dataOut.flush();
            } catch (IOException e) {
                System.out.println("IOException from sendButtonNum() : ServerSideConnection");
            }
        }



        // I ASKED chatgtp to give be a better fromat instead of 2 sets fo 9 if statments
        public void processGameLogicP1(String input) {
            placeMove(input, 'X');  // P1 uses 'X'
        }

        public void processGameLogicP2(String input2) {
            placeMove(input2, 'O');  // P2 uses 'O'
        }

        private void placeMove(String input, char symbol) {
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



    }




    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();

    }
}
