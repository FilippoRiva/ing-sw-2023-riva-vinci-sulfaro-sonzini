package it.polimi.myshelfie.network;

import it.polimi.myshelfie.view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents the Client implementation for RMI. It has a view associated and an object (lifeLineSync) used for the
 * connection.
 */
public class ClientImpl extends UnicastRemoteObject implements Client {
    private final View view;
    private final Object lifelineSync = new Object();

    /**
     * Constructor.
     *
     * @param view the view.
     * @throws RemoteException
     */
    public ClientImpl(View view) throws RemoteException {
        this.view = view;
    }

    @Override
    public String getHostName() throws RemoteException {
        return System.getProperty("java.rmi.server.hostname");
    }

    @Override
    public void handshake(ServerConnectionHandler server) throws RemoteException {
        new Thread(() -> {
            try {
                server.lifeline();
            } catch (RemoteException e) {
                view.stopUI();
            }
        }).start();

        new Thread(() -> view.addObserver((evt, message) -> { //adding view observer
            if (evt == null) return;
            try {
                server.send(message);
            } catch (RemoteException ignored) {
                System.out.println("View Observer Failure. Unable to send Network Message to the server");
            }
        })).start();

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(()->{
            try {
                server.send(new NetworkMessage()); //ping message
            } catch (RemoteException ignored) {
                System.out.println("Ping Failure. Unable to send Ping Network Message to the Server");
                service.shutdown();
            }
        }, 1, 5, TimeUnit.SECONDS);
    }

    @Override
    public synchronized void send(NetworkMessage networkMessage) throws RemoteException {
        networkMessage.clientExecute(view);
    }

    @Override
    public void lifeline() throws RemoteException {
        //this method blocks forever the calling thread until the client disconnects
        synchronized (lifelineSync) {
            try {
                lifelineSync.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
