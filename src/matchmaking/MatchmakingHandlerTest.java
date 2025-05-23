//package matchmaking;
//
//import account.*;
//import database.DatabaseManager;
//import game.GameType;
//
//import java.util.concurrent.TimeUnit;
//
//public class MatchmakingHandlerTest {
//    public static void main(String[] args) throws InterruptedException {
////        // Initialize test account 1
////        int id1 = 123;
////        String username1 = "Alice";
////        String email1 = "Alice@gmail.com";
////        String pass1 = "alice123";
////        Account testAccount1 = new Account(id1, username1, email1, pass1);
////
////        // Initialize test account 2
////        int id2 = 234;
////        String username2 = "Bob";
////        String email2 = "Bob@gmail.com";
////        String pass2 = "bob123";
////        Account testAccount2 = new Account(id2, username2, email2, pass2);
////
////        // Add match outcome for both accounts
////        GameType game = GameType.CHESS;
////
////        HashMap<StatisticType, Integer> account1Results = new HashMap<>();
////        account1Results.put(StatisticType.WINS, 1);
////
////        HashMap<StatisticType, Integer> account2Results = new HashMap<>();
////        account2Results.put(StatisticType.LOSSES, 1);
////
////        try {
////            MatchOutcomeHandler.RecordMatchOutcome(game, true, testAccount1, account1Results,testAccount2, account2Results);
////        } catch (MatchOutcomeInvalidError e){
////            System.out.println(e);
////        }
//
//
//        // NOTE: Since I haven't implemented threading, you will have to run two instances of this class to get ...
//        // the test to work. This is because the current thread will not halt on the call to .startMatchmaking until
//        // the matchmaking process is complete or cancelled. Please ask me (Elijah) if you have any questions.
//
////        // Add matchmaking table handlers for account 1
////        matchmaking.MatchmakingHandler account1MM = new matchmaking.MatchmakingHandler(testAccount1, "");
////
////        try {
////            account1MM.startMatchmaking(game, testAccount1.getElo(game));
////            System.out.println("Finished matchmaking for account 1");
////        } catch (Exception e){
////            System.out.println(e);
////        }
//
////        // Add matchmaking table handlers for account 2
////        matchmaking.MatchmakingHandler account2MM = new matchmaking.MatchmakingHandler(testAccount2, "");
////
////        try {
////            account2MM.startMatchmaking(game, testAccount2.getElo(game));
////            System.out.println("Finished matchmaking for account 2");
////        } catch (Exception e){
////            System.out.println(e);
////        }
//
//        // Load accounts
//        Account testAccount1 = DatabaseManager.queryAccountByID(4);
//        Account testAccount2 = DatabaseManager.queryAccountByID(5);
//
//        // Insert host, client into database
//        DatabaseManager.saveAccount(testAccount1);
//        DatabaseManager.saveAccount(testAccount2);
//        Account testAccount3 = new Account();   // Guest account
//        Account testAccount4 = new Account();   // Guest account
//
//        // Test host, client
////        matchmaking.MatchmakingHandler handler1 = new matchmaking.MatchmakingHandler();
////        matchmaking.MatchmakingHandler handler2 = new matchmaking.MatchmakingHandler();
////        matchmaking.MatchmakingHandler handler3 = new matchmaking.MatchmakingHandler(); // Guest account
////        matchmaking.MatchmakingHandler handler4 = new matchmaking.MatchmakingHandler(); // Guest account
//
//        String code = testAccount1.getMatchmakingHandler().getUniqueRoomCode();
//        testAccount1.getMatchmakingHandler().startHosting(testAccount1.getID(), GameType.CHESS, code, "");
//        TimeUnit.SECONDS.sleep(1);
//        testAccount2.getMatchmakingHandler().tryJoinHost(testAccount2.getID(), code);
//
//
////        HostThread host = new HostThread(testAccount1.getMatchmakingHandler(), code, "", testAccount1.getID());
////        host.start();
////
////        TimeUnit.SECONDS.sleep(1);
////
////        ClientThread client = new ClientThread(testAccount2.getMatchmakingHandler(), code, "", testAccount2.getID());
////        client.start();
//
////        int tempID1 = DatabaseManager.getTempID();
////        HostThread host = new HostThread(handler4, code, "", tempID1);
////        host.start();
////
////        TimeUnit.SECONDS.sleep(1);
////
////        int tempID2 = DatabaseManager.getTempID();
////        ClientThread client = new ClientThread(handler3, code, "", tempID2);
////        client.start();
//    }
//}
//
//class HostThread extends Thread {
//    private final MatchmakingHandler host;
//    private final String roomCode;
//    private final String networkingInfo;
//    private final int id;
//
//    public HostThread(MatchmakingHandler host, String roomCode, String networkingInfo, int id){
//        this.host = host;
//        this.roomCode = roomCode;
//        this.networkingInfo = networkingInfo;
//        this.id = id;
//    }
//    public void run(){
//        try {
//            host.startHosting(id, GameType.CHESS, roomCode, networkingInfo);
//            System.out.println("[Host] Client has joined!");
//        } catch (Exception e){
//            System.out.println(e);
//        }
//    }
//}
//
//class ClientThread extends Thread {
//    private final MatchmakingHandler client;
//    private final String roomCode;
//    private final String networkingInfo;
//    private final int id;
//
//    public ClientThread(MatchmakingHandler client, String roomCode, String networkingInfo, int id){
//        this.client = client;
//        this.roomCode = roomCode;
//        this.networkingInfo = networkingInfo;
//        this.id = id;
//    }
//    public void run(){
//        try {
//            if(client.tryJoinHost(id, roomCode)){
//                System.out.println("[Client] Client has joined!");
//            }
//            else {
//                System.out.println("Client failed to connect to host");
//            }
//        } catch (Exception e){
//            System.out.println(e);
//        }
//    }
//}