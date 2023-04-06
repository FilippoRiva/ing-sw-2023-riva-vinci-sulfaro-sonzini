package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.interfaces.Controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMI extends UnicastRemoteObject implements Server{
    private final Controller controller;
    public ServerRMI(Controller controller) throws RemoteException{
        super();
        this.controller = controller;
    }
    @Override
    public synchronized void connect(Client client) throws RemoteException {
        new Thread(()->{
            try {
                ServerConnectionHandlerRMI s = new ServerConnectionHandlerRMI(client,controller);
                s.start();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
