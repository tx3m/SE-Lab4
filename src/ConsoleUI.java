import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.StringTokenizer;

/**
 * This class is the player's console user interface class 
 * that consists of the main player loop (Renz),
 * a parser reading from System.in (K�lling/Barnes)
 * and prints to System.out (Renz).
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * The parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kolling and David J. Barnes, modified by Wolfgang Renz
 * @version 2011.07.31, 2019.05.16
 */

public class ConsoleUI implements PlayerUI
{
    private CommandWords commands;  // holds all valid command words
    private Player player;

    public ConsoleUI(Player p) 
    {
        commands = new CommandWords();
        player = p;
    }

    public void start() throws RemoteException {
        boolean finished = false;
        while(! finished) {
            //finished= nextCommand();
            Command command = getCommand();
            if(command == null) {
                println("I don't understand...");
            } 
            else {
                finished = command.execute(player);
            }
        }
    }

    public void println(String s)
    {
        System.out.println(s);
    }

    private Command getCommand()
    {
        String inputLine = "";   // will hold the full input line
        String word1;
        String word2;

        System.out.print(player.getName()+"> ");     // print prompt

        BufferedReader reader = 
            new BufferedReader(new InputStreamReader(System.in));
        try {
            inputLine = reader.readLine();
        }
        catch(java.io.IOException exc) {
            System.err.println ("There was an error during reading: "
                + exc.getMessage());
        }

        StringTokenizer tokenizer = new StringTokenizer(inputLine);

        if(tokenizer.hasMoreTokens())
            word1 = tokenizer.nextToken();      // get first word
        else
            word1 = null;
        if(tokenizer.hasMoreTokens())
            word2 = tokenizer.nextToken();      // get second word
        else
            word2 = null;

        // note: we just ignore the rest of the input line.

        Command command = commands.get(word1);
        if(command != null) {
            command.setSecondWord(word2);
        }
        return command;
    }
}
