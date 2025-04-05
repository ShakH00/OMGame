//package database;
//
//import game.Player;
//
//import java.util.ArrayList;
//
//public class PlayerData {
//
//    public static ArrayList<Player> players = new ArrayList<>();
//
//    //Method to add a player to the database
//    public static void addPlayer(Player player) {
//        players.add(player);
//    }
//
//    //Find any player in the database
//    public static Player findPlayer(String username) {
//        for (int i = 0; i < players.size(); i++) {
//            if (players.get(i).getAccount().getUsername().equals(username)) {
//                return players.get(i);
//            }
//        }
//        return null;
//    }
//
//    //Remove any player from the database
//    public static void removePlayer(Player player) {
//        for (int i = 0; i < players.size(); i++ ) {
//            if (players.get(i).equals(player)) {
//                //Changes the player to null
//                players.remove(i);
//            }
//        }
//    }
//}
