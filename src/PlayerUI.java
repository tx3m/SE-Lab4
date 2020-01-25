import java.rmi.RemoteException;

/**
 * Most simple Interface PlayerUI.
 * Currently, only text writing is supported,
 * because the rest of the game is text-output oriented.
 * 
 * For future versions, the game and command classes 
 * should communicate semantics to the player who can forward it
 * in an appropriate way to the PlayerUI
 *
 * @author Wolfgang Renz
 * @version 2019.05.16
 */
public interface PlayerUI
{
    void println(String s);
    public void start() throws RemoteException;
}
