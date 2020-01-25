import java.rmi.RemoteException;

/**
 * Implementation of the 'quit' user command.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */
public class QuitCommand extends Command
{
    /**
     * Constructor for objects of class QuitCommand
     */
    public QuitCommand()
    {
    }

    /**
     * "Quit" was entered. Check the argument to see whether
     * we really quit the game. Return true, if we should quit, false
     * otherwise.
     */
    public boolean execute(Player player)
    {
        if(getSecondWord() == null) {
            try {
                player.quit();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return true;
        }
        else {
            player.println("I cannot quit that...");
            return false;
        }
    }

}
