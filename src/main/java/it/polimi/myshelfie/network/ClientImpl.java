package it.polimi.myshelfie.network;

import it.polimi.myshelfie.view.View;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {
    View view;

    public ClientImpl(View view) throws RemoteException {
        this.view = view;
    }

    @Override
    public void handshake(ServerConnectionHandler server) throws RemoteException {
        new Thread(()->{
            try {
                server.lifeline();
            } catch (RemoteException e) {
                view.stopUI();
            }
        }).start();
        new Thread(()-> view.addObserver( (evt,message)->{
            if(evt==null) return;
            try {
                server.send(message);
            } catch (RemoteException ignored) {
            }
        })).start();
    }

    @Override
    public synchronized void send(NetworkMessage networkMessage){
        networkMessage.clientExecute(view);
    }

    @Override
    public void lifeline() throws RemoteException {
        //this method blocks forever
        Object obj = new Object();
        synchronized (obj){
            try {
                obj.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
