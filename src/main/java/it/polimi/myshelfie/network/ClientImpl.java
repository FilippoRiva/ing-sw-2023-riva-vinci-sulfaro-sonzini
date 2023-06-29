package it.polimi.myshelfie.network;

import it.polimi.myshelfie.view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The Client implementation. It has a view associated and a lock object (lifeLineSync) used for the
 * connection lifeline.
 */
public class ClientImpl extends UnicastRemoteObject implements Client {
    private final View view;
    private final Object lifelineSync = new Object();

    /**
     * Constructor.
     *
     * @param view the view.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    public ClientImpl(View view) throws RemoteException {
        this.view = view;
    }

    /**
     * {@inheritDoc}
     * @return the host name set in the java.rmi.server.hostname system property
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    @Override
    public String getHostName() throws RemoteException {
        return System.getProperty("java.rmi.server.hostname");
    }

    /**
     * {@inheritDoc}
     * @param server the server connection handler.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
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
                service.shutdown();
            }
        }, 1, 5, TimeUnit.SECONDS);
    }

    /**
     * {@inheritDoc}
     * @param networkMessage the message to send.
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
    @Override
    public synchronized void send(NetworkMessage networkMessage) throws RemoteException {
        networkMessage.clientExecute(view);
    }

    /**
     * {@inheritDoc}
     * @throws RemoteException if a communication-related exceptions has occurred.
     */
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
