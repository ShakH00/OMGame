package database;

import game.Player;

import java.util.ArrayList;

public class PlayerData {

    public static ArrayList<Player> players = new ArrayList<>();

    //Method to add a player to the database
    public static void addPlayer(Player player) {
        players.add(player);
    }


}
