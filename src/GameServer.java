import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;

public class GameServer {
    public static void main(String [] args) throws Exception {
        LocateRegistry.createRegistry( Registry.REGISTRY_PORT );
        RemoteServer.setLog( System.err );

        Game server = new Game();
        Naming.rebind("game", server);
    }
}
