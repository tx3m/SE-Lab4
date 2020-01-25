import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGame extends Remote, Observable {
    //setState(IPlayer p)
    public void addPlayer(IPlayer p) throws RemoteException;
    public void deletePlayer(IPlayer p) throws RemoteException;
    //void startGame () throws RemoteException;
    public void setPlayerRoom(Player p, IRoom r) throws RemoteException;
}
