import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.
 * 
 * This class holds a collection of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    private HashMap<String, Command> commands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        commands = new HashMap<String, Command>();
        commands.put("go", new GoCommand());
        commands.put("help", new HelpCommand(this));
        commands.put("quit", new QuitCommand());
        commands.put("pick", new PickCommand());
    }

    /**
     * Given a command word, find and return the matching command object.
     * Return null if there is no command with this name.
     */
    public Command get(String word)
    {
        return (Command)commands.get(word);
    }

    /**
     * Print all valid commands to System.out.
     */
    public String toString() 
    {
        StringBuffer buf= new StringBuffer();
        for (String s : commands.keySet()) {
            buf.append(s);
            buf.append("  ");
        }
        buf.append("\n");
        return new String(buf);
    }
}
