import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Write a description of interface Observer here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Observer extends Remote
{
    /**
     * This method will update all Players with the changes in Game
     */
   void update(IRoom room) throws RemoteException;
   void println(String s) throws RemoteException;
}
