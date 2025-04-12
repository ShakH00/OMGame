/* package networking.test.networkingObjectsSending;

import game.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GameServer {
    private final HashMap<Integer, String> chatLogs;
    private ServerSocket gameServerSocket;
    private ServerSocket chatServerSocket;
    private int numPlayers;
    private ServerSideConnection player1SSC;    // these a the server side equilivant to the package drop off pouints i think
    private ServerSideConnection player2SSC;
    private ServerSideConnection player1Chat;
    private ServerSideConnection player2Chat;
    // store the  button num that the player clicked on, befroe being sent to the other player
    // don in the run method while loop, for each turns
    private Game game;


    public GameServer() {
        System.out.println("--Game Server--");
        chatLogs = new HashMap<>();
        numPlayers = 0;


        try {
            gameServerSocket = new ServerSocket(30000);
            chatServerSocket = new ServerSocket(30001);

        } catch (IOException e) {
            System.out.println("IOException from game server constructor");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();

    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections");
            while (numPlayers < 2) {
                Socket gameSocket = gameServerSocket.accept();
                Socket chatSocket = chatServerSocket.accept();
                numPlayers++;

                System.out.println("Player #" + numPlayers + " has joined the game");
                ServerSideConnection ssc = new ServerSideConnection(gameSocket, chatSocket, numPlayers);

                if (numPlayers == 1) {
                    player1SSC = ssc;
                } else {
                    player2SSC = ssc;
                }

                Thread t = new Thread(ssc); //what ever is the in the ssc run in the new "THREAD"
                t.start();
            }
            System.out.println("2 player reached, no longer looking for players");
        } catch (IOException e) {
            System.out.println("IOException from game server acceptConnections");
        }
    }

    private class ServerSideConnection implements Runnable {
        private final Socket gameSocket;
        private final Socket chatSocket;
        private final int playerID;
        private DataOutputStream dataOut;
        private ObjectOutputStream gameOutObj;
        private ObjectInputStream gameInObj;
        private ObjectOutputStream chatOutObj;
        private ObjectInputStream chatInObj;

        public ServerSideConnection(Socket gameSocket1, Socket chatSocket1, int id) {
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

        public void run() { // Instructions to run on a NEW thread
            try {
                System.out.println("Sent player ID: " + playerID);
                dataOut.writeInt(playerID);

                new Thread(() -> {
                    handleChatThread();
                }).start();

                while (true) {
                    if (playerID == 1) {
                        game = (game) gameInObj.readObject();  // Reads one char and converts to String // read it from player 1
                        player2SSC.sendGame(); // sending server2dChar
                    } else {
                        game = (game) gameInObj.readObject();
                        player1SSC.sendGame();
                    }

                    if (false) {
                        System.out.println("GAME HAS ENDED");
                        break; // break from the game and end
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException from run(): ServerSideConnection");
            } catch (ClassNotFoundException e) {
                System.out.println("ClassNotFoundException from run(): ServerSideConnection");
            }
        }

        public void sendGame() {
            try {
                gameOutObj.writeObject(game);
                gameOutObj.flush();
            } catch (IOException e) {
                System.out.println("IOException from game server sendGame");
            }
        }


        public void handleChatThread() {
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
                    if (this.playerID == 1 && player2SSC != null) {
                        player2SSC.sendChatMessage(msg);
                    } else if (this.playerID == 2 && player1SSC != null) {
                        player1SSC.sendChatMessage(msg);
                    }
                }
            } catch (IOException e) {
                System.out.println("Chat thread crashed for Player " + playerID);
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Object not found");
            }
        }

        public void sendChatMessage(String msg) {
            try {
                chatOutObj.writeObject(msg);  // Send the censored message
                chatOutObj.flush();

            } catch (IOException e) {
                System.out.println("Error sending message to player #" + playerID);
                e.printStackTrace();
            }
        }

    }
}*/
