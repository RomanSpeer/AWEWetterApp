/**
 *
 * Server-Klasse
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
     *
     */
    private RConnection c;
    private static Server serverInstance;

    /**
     *
     */
    private static Server() {
    }

    //Singelton-Sever erstellen/ zurückgeben

    /**
     *
     * @return
     */
    public static Server getServer()
    {
        //Prüfen ob der Server null ist
        if (serverInstance == null)
        {
            //Neuen Server erstellen
            serverInstance = new Server();
        }
        return serverInstance;
    }

    /**
     *
     * @param path
     * @param port
     * @throws REXPMismatchException
     * @throws REngineException
     */
    public void connect(String path, int port) throws REXPMismatchException, REngineException
    {
        //Neue Verbindung herstellen
        c = new RConnection();

        //Verbindung prüfen
        if(c.isConnected())
            System.out.println("Verbindung wurde aufgebaut");

        // R-Version ermitteln und ausgeben:
        REXP x = c.eval("R.version.string");
        System.out.println(x.asString());
    }

    /**
     *
     * @return
     */
    public boolean isConnected()
    {
        if(c.isConnected())
            return true;
        else
            return false;
    }

    /**
     *
     * @return
     */
    public boolean closeConnection()
    {
        if(c.isConnected())
        {
            return c.close();
        }
        return false;
    }
}
