//import org.jetbrains.annotations.Contract;

import java.io.*;
import java.rmi.*;
import java.util.*;
import java.rmi.server.* ;

/**
 *  This class is the game class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  This class creates the game environment consisting 
 *  of a simple labyrinth of rooms and returns its Instance to the player.
 *  It also welcomes and farewells the player.
 * 
 * @author  Michael Kolling and David J. Barnes, modified by Wolfgang Renz
 * @version 2011.07.31, 2019.05.16
 */

public class Game extends UnicastRemoteObject implements Observable, IGame
{
   // private Game game;
    private HashMap<IPlayer,IRoom> playersMap;
    public Room startRoom;
    boolean gameStarted = false;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() throws RemoteException {
        //super();
        if(!gameStarted){
            startRoom= createRooms();
            playersMap = new HashMap<IPlayer, IRoom>();
        }
    }


    public void startGame(){
        startRoom = createRooms();
        playersMap = new HashMap<IPlayer, IRoom>();
        gameStarted = true;
    }

    public void setPlayerRoom(Player p, IRoom r){
        playersMap.put(p,r);
    }

    //@Contract(pure = true)
//    public static HashMap<IPlayer, IRoom> getPlayersMap() {
//        return playersMap;
//    }

//     public static Game getGame() throws RemoteException {
//         if(game == null){
//             game = new Game();
//         }
//         return game;
//     }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room outside, theatre, pub, lab, office, library;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        library = new Room("in the BT7 library");

        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        //outside.setExit("upstairs", library);

        library.setExit("downstairs", outside);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        return outside;
    }

    /**
     * Print out the opening message for the player.
     */
    private void welcome(IPlayer player) throws RemoteException {
        player.setCurrentRoom(startRoom);
        player.setPreviousRoom(startRoom);
        player.println("");
        player.println("Welcome to The World of Zuul!");
        player.println("The World of Zuul is a new, incredibly boring adventure game.");
        player.println("Type 'help' if you need help.(only for ConsoleUI)");
        player.println("");
        player.println(player.getCurrentRoom().getLongDescription());
    }

    private void farewell(IPlayer p) throws RemoteException
    {
        try {
            p.println(" thank you for playing.  Good bye."); // p.getName() +
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(IPlayer p) throws RemoteException {
        welcome(p);
        //game.
        playersMap.put(p, p.getCurrentRoom());
    }

    public void deletePlayer(IPlayer p) throws RemoteException
    {
        farewell(p);
        if(p.equals(playersMap.get(p))) {
            farewell(p);
        }
        System.out.println("Player " + " deleted!"); // + p.getName()
        if(playersMap.size() == 1){
            System.out.println("Thank you for playing. Have a good day!");
        };
        playersMap.remove(p);
        notifyPlayers();
    }

    public void notifyPlayers(){
        for (IPlayer observer : playersMap.keySet()){
            Player p = (Player) observer; // holds the last added player
            for (IPlayer player: playersMap.keySet()) {
                //if we have two different players AND they are in the same room
                try {
                    if (!player.equals(p) && player.getCurrentRoom().equals(p.getCurrentRoom())) {
                        //if this player did not move AND someone else entered the room
                        if (p.getCurrentRoom().equals(p.getPreviousRoom())) {
                            p.println("Player "  + " entered this room!"); //+ player.getName()
                        } else { //the this player entered a room and there is another player here
                            p.println("Player " + " is in this room!"); //+ player.getName()
                        }
                        //if it is two different players
                    } else if (!player.equals(p)) {
                        //
                        if (p.getCurrentRoom().equals(player.getPreviousRoom())) {
                            p.println("Player " + " left this room!"); // +player.getName()
                            continue;
                        }
                        if (player.getCurrentRoom().equals(p.getPreviousRoom())) {
                            {
                                player.println("Player " + p.getName() + " left this room!");
                                continue;
                            }
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            try {
                observer.update(playersMap.get(observer).getRoom());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
