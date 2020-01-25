import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRoom extends Remote {
    public String getShortDescription() throws RemoteException;
    public String getLongDescription() throws RemoteException;
    public Room getExit(String direction) throws RemoteException;
    public Room getRoom() throws RemoteException;
}
