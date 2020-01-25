import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Write a description of interface Observable here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Observable extends Remote
{
    /**
     * Those 3 methods will be used to add Players, delete Players and send a notification to all registered players
     */
    void addPlayer(IPlayer p) throws RemoteException;
    void deletePlayer(IPlayer p) throws RemoteException;
    void notifyPlayers() throws RemoteException;
}
