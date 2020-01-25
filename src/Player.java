import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * This class represents players in the Zuul game and is the class 
 * to instantiate. 
 * Each player has a current location in the game provided by the game class.
 * In the beginning, the player registers with the game environment.
 * The player class also instantiates two user interfaces,
 * a text-based console and a text-based GUI.
 * 
 * On first use, the console user interface is recommended to use.
 * Since the game environment is awfully simple, it is recommended to extend
 * the game environment, put items into the rooms, let the user collect items
 * up to a weight limit into his/her rucksack, and to let the player win when
 * she is able to find the required items and bring them into the goal room.
 * 
 * @author  Michael Kolling and David J. Barnes, modified by Wolfgang Renz
 * @version 2011.07.31, 2019.05.16
 */

public class Player implements Observer, IPlayer, Serializable
{
    private String name;
    private IRoom currentRoom;
    private IRoom previousRoom;
    private PlayerUI cui;
    private PlayerUI gui;
    private IGame game;

    /**
     * Constructor for objects of class Player
     */
    public Player(String name, boolean flagCUI) throws RemoteException {
        this.name= name;
        if(flagCUI) cui= new ConsoleUI(this);

        GUI ui = new GUI(this);
        gui = ui;


        register();
    }

    /**
     * Constructor for objects of class Player
     */
    public Player(String name) throws RemoteException {
        this(name, false);
    }

    private void register() throws RemoteException {
        try {
            game  = (IGame) Naming.lookup("rmi://localhost/game");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        game.addPlayer(this);
    }

    public String getName()
    {
        return this.name;
    }

    public void println(String s)
    {
        if(gui!= null) gui.println(s);
    }

    /**
     * Return the current room for this player.
     */
    public IRoom getCurrentRoom(){return currentRoom;}

    /**
     * Set the current room for this player.
     */
    public void setCurrentRoom(IRoom room) throws RemoteException {
        currentRoom = room;
        game.setPlayerRoom(this,room);
    }

    /**
     * Try to walk in a given direction. If there is a door
     * this will change the player's location.
     */
    public void walk(String direction) throws RemoteException {
        // Try to leave current room.
        IRoom nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            println("There is no door!");
        }
        else {
            setCurrentRoom(nextRoom);
            println(nextRoom.getLongDescription());
            game.notifyPlayers();
        }
    }

    public IRoom getPreviousRoom() {
        return previousRoom;
    }

    public void setPreviousRoom(IRoom previousRoom) {
        this.previousRoom = previousRoom;
    }

    public void quit() throws RemoteException
    {
        game.deletePlayer(this);
    }

    public void addMe (){
        try {
            game.addPlayer(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void usage()
    {
        System.out.println("First Usage:");
        System.out.println("Player \"Zuul\"");  
        System.out.println("Later you can change it in the "
            +"batch command file to:");
        System.out.println("Player [-GUI] YourPlayerName");                      
    }

    /*Updates the Status of room*/
    public void update(IRoom room){
        this.setPreviousRoom(this.currentRoom);
        this.currentRoom = room;
    }

    public static void main(String [] argv) throws RemoteException {
        boolean flagCUI=false;
        String name;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter player name:");
        name = scan.nextLine();

        //What does this loop do?
        for(int i=0; i<argv.length; i++){
            if ( argv[i].equals("-GUI") ) flagCUI=true;
            else name= argv[i];
        }

        new Player(name, flagCUI);
    }
}
