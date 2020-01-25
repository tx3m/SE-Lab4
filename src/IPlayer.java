import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayer extends Remote {
    public void setCurrentRoom(IRoom r) throws RemoteException;
    public void setPreviousRoom(IRoom r) throws RemoteException;
    public void println(String s) throws RemoteException;
    public IRoom getCurrentRoom() throws RemoteException;
    public void update(IRoom room) throws RemoteException;
    public IRoom getPreviousRoom() throws RemoteException;
}
