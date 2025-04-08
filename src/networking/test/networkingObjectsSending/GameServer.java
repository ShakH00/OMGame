package networking.test.networkingObjectsSending;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
    private ServerSocket gameServerSocket;
    private ServerSocket chatServerSocket;
    private int numPlayers;
    private ServerSideConnection player1Ssc;    // these a the server side equilivant to the package drop off pouints i think
    private ServerSideConnection player2Ssc;

    private ServerSideConnection player1Chat;
    private ServerSideConnection player2Chat;


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
            gameServerSocket = new ServerSocket(30000);
            chatServerSocket = new ServerSocket(30001);

        } catch(IOException e){
            System.out.println("IOException from game server constructor");
            e.printStackTrace();
        }
    }




    public void acceptConnections(){
        try {
            System.out.println("waiting for connections");
            while (numPlayers < 2) {
                Socket gameSocket = gameServerSocket.accept();
                Socket chatSocket = chatServerSocket.accept();
                numPlayers++;

                System.out.println("Player #" + numPlayers + " has joined the game");
                ServerSideConnection ssc = new ServerSideConnection(gameSocket, chatSocket, numPlayers);

                if (numPlayers == 1) {
                    player1Ssc = ssc;

                } else {

                    player2Ssc = ssc;
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
        private Socket gameSocket;
        private Socket chatSocket;

        private DataOutputStream dataOut;
        private ObjectOutputStream gameOutObj;
        private ObjectInputStream gameInObj;

        private ObjectOutputStream chatOutObj;
        private ObjectInputStream chatInObj;

        private int playerID;

        public ServerSideConnection(Socket gameSocket1, Socket chatSocket1, int id){
            gameSocket = gameSocket1;
            chatSocket = chatSocket1;
            playerID = id;

            try {
                dataOut = new DataOutputStream(gameSocket.getOutputStream());
                gameOutObj = new ObjectOutputStream(gameSocket.getOutputStream());
                gameInObj = new ObjectInputStream(gameSocket.getInputStream());


                 chatOutObj = new ObjectOutputStream(chatSocket.getOutputStream());
                 chatInObj = new ObjectInputStream(chatSocket.getInputStream());
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

                new Thread(() -> {
                    handleChatThread();
                }).start();

                while (true) {
                    if(playerID == 1){
                        practiceGameObj = (PracticeGameObj) gameInObj.readObject();  // Reads one char and converts to String // read it from player 1
                        player1ButtonNum = practiceGameObj.getTestString();
                        System.out.println("Payer 1 clicked button #" + player1ButtonNum);
                        // Update array
                        processGameLogic(1);
                        player2Ssc.sendPracticeGameObj(); // sending server2dChar


                    }
                    else{
                        practiceGameObj = (PracticeGameObj) gameInObj.readObject();
                        player2ButtonNum = practiceGameObj.getTestString();
                        System.out.println("PLayer 2 clicked button #" + player2ButtonNum);

                        processGameLogic(2);

                        player1Ssc.sendPracticeGameObj();
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
                gameOutObj.writeObject(practiceGameObj);
                gameOutObj.flush();
            } catch (IOException e){
                System.out.println("IOException from game server sendPracticeGameObj");
            }
        }

       public void handleChatThread(){
            System.out.println(chatLogs.toString());
            receiveChats();

        }


        public void receiveChats() {
            try {
                while (true) {
                    Object obj = chatInObj.readObject(); // ✅
                    String msg = (String) obj;
                    msg = censorChat(msg);  // Apply censorship here

                    chatLogs.put(this.playerID, msg);  // Store censored message in chat logs
                    System.out.println("PUT Chat Player #" + this.playerID + ": " + msg);

                    // Send the censored message to the other player
                    if (this.playerID == 1 && player2Ssc != null) {
                        player2Ssc.sendChatMessage(msg);
                    } else if (this.playerID == 2 && player1Ssc != null) {
                        player1Ssc.sendChatMessage(msg);
                    }
                }
            } catch (IOException e) {
                System.out.println("Chat thread crashed for Player " + playerID);
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Object not found");
            }
        }

        public void sendChatMessage(String msg){
            try {
                chatOutObj.writeObject(msg);  // Send the censored message
                chatOutObj.flush();

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
