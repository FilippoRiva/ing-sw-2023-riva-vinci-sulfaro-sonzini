package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.utils.ServerLogger;

import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

/**
 * Represents the Server implementation.
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    private final Controller controller;

    /**
     * Constructor.
     *
     * @param controller the controller.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    public ServerImpl(Controller controller) throws RemoteException {
        super();
        this.controller = controller;
    }

    @Override
    public synchronized void connect(Client client) throws RemoteException {
        new Thread(() -> {
            try {
                ServerConnectionHandlerImpl s = new ServerConnectionHandlerImpl(client, controller);
                s.start();
            } catch (UnmarshalException e) {
                ServerLogger.log(Level.INFO, "Client trying to connect with unsupported class versions");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
