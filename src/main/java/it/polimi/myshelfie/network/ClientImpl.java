package it.polimi.myshelfie.network;

import it.polimi.myshelfie.view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientImpl extends UnicastRemoteObject implements Client {
    private final View view;
    private final Object lifelineSync = new Object();

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
            }
        })).start();

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            try {
                server.send(new NetworkMessage()); //ping message
            } catch (RemoteException ignored) {
            }
        }, 1, 5, TimeUnit.SECONDS);
    }

    @Override
    public synchronized void send(NetworkMessage networkMessage) throws RemoteException {
        networkMessage.clientExecute(view);
    }

    @Override
    public void lifeline() throws RemoteException {
        //this method blocks forever
        synchronized (lifelineSync) {
            try {
                lifelineSync.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
