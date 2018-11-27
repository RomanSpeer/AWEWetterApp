package de.fhdw.steffen.awewetter.classes;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.REngineException;

public class Server {

    private RConnection c;
    public Server server;

    public Server() {
    }

    public Server getServer() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public void connect(String path, int port) throws REXPMismatchException, REngineException {
        // Verbindung aufbauen:
        c = new RConnection();
        // Verbindung pr�fen:
        if(c.isConnected())
            System.out.println("Verbindung wurde aufgebaut");
        // R-Version ermitteln und ausgeben:
        REXP x = c.eval("R.version.string");
        System.out.println(x.asString());
    }

    public boolean isConnected() {
        if(c.isConnected())
            return true;
        else
            return false;
    }

    public boolean closeConnection() {
        if(c.isConnected()) {
            return c.close();
        }
        return false;
    }


}
