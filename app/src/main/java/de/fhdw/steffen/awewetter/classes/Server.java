/**
 * Server-Klasse
 * Erstellt ein Singelton Object, welches die Serververbindung enthält.
 *
 * @author Roman Speer
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.classes;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.REngineException;

public class Server {

    /**
     *
     */
    private RConnection c;
    public Server server;



    /**
     *  Erstellen/ zurückgeben des Singelton-Servers
     *
     * @return
     */
    public Server getServer() {
        //Prüfen ob der Server null ist
        if (server == null) {
            //Neuen Server erstellen
            server = new Server();
        }
        return server;
    }

    /**
     * Zurückgeben der Serverconnection
     *
     * @return
     */
    public RConnection getConnection() {
        return c;
    }


    /**
     *
     * Erstellung einer neuen Serververbindung
     *
     * @param path
     * @throws REXPMismatchException
     * @throws REngineException
     */
    public void connect(String path) throws REXPMismatchException, REngineException {
        //Neue Verbindung herstellen
        c = new RConnection(path);
    }

    /**
     *
     * Zurückgeben ob der Server mit dem Client verbunden ist
     *
     * @return
     */
    public boolean isConnected() {
        if (c.isConnected())
            return true;
        else
            return false;
    }

    /**
     *
     * Schließen der Verbindung zwischen Server und Client
     *
     * @return
     */
    public boolean closeConnection() {
        if (c.isConnected()) {
            return c.close();
        }
        return false;
    }
}
