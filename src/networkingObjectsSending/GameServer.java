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
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


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

        // Censorship Logic
        public String censorChat(String message) {
            List<String> badWords = new ArrayList<>();
            int censorCount = 0;

            try (BufferedReader br = new BufferedReader(new FileReader("networking/badwords.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    badWords.add(line.trim());
                }
            } catch (IOException e) {
                System.err.println("Error loading bad words: " + e.getMessage());
            }

            String filteredMessage = message;

            for (String badWord : badWords) {
                String censor = generateCensor(badWord);

                // Censor normal word
                Pattern exactPattern = Pattern.compile("(?i)\\b" + Pattern.quote(badWord) + "\\b");
                Matcher exactMatcher = exactPattern.matcher(filteredMessage);
                while (exactMatcher.find()) {
                    filteredMessage = exactMatcher.replaceFirst(censor);
                    censorCount++;
                    exactMatcher = exactPattern.matcher(filteredMessage); // reset matcher
                }

                // Censor spaced/dotted/etc. bypass versions
                Pattern bypassPattern = Pattern.compile(buildBypassRegex(badWord), Pattern.CASE_INSENSITIVE);
                Matcher bypassMatcher = bypassPattern.matcher(filteredMessage);
                while (bypassMatcher.find()) {
                    filteredMessage = bypassMatcher.replaceFirst(censor);
                    censorCount++;
                    bypassMatcher = bypassPattern.matcher(filteredMessage); // reset matcher
                }
            }

            return filteredMessage;
        }

        public String generateCensor(String word) {
            if (word.length() <= 1) return "*";
            StringBuilder censored = new StringBuilder();
            censored.append(word.charAt(0));
            for (int i = 1; i < word.length(); i++) {
                censored.append("*");
            }
            return censored.toString();
        }

        public String buildBypassRegex(String word) {
            StringBuilder regex = new StringBuilder();
            for (char c : word.toCharArray()) {
                regex.append(Pattern.quote(String.valueOf(c)));
                regex.append("[^a-zA-Z0-9]{0,3}");
            }
            return regex.toString();
        }
        // End of censorship logic

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


        public void receiveChats() {
            try {
                while (true) {
                    Message message = (Message) objectIn.readObject();
                    String msg = message.getMessage();

                    // Censor chat message before logging or sending
                    msg = censorChat(msg);  // Apply censorship here

                    chatLogs.put(playerID, msg);  // Store censored message in chat logs

                    System.out.println("Chat Player #" + playerID + ": " + msg);

                    // Send the censored message to the other player
                    if (playerID == 1 && player2 != null) {
                        player2.sendChatMessage(msg);
                    } else if (playerID == 2 && player1 != null) {
                        player1.sendChatMessage(msg);
                    }
                }
            } catch (IOException e) {
                System.out.println("Chat thread crashed for Player " + playerID);
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public void sendChatMessage(String msg){
            try {
                dataOut.writeUTF(msg);  // Send the censored message
                dataOut.flush();
            } catch (IOException e) {
                System.out.println("Error sending message to player #" + playerID);
                e.printStackTrace();
            }
        }


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
