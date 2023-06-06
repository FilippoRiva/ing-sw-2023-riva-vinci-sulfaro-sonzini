package it.polimi.myshelfie.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client{
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public ClientSkeleton(Socket socket) throws RemoteException {
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.oos.flush();
        } catch (IOException e) {
            throw new RemoteException("Cannot create output stream", e);
        }
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create input stream", e);
        }
    }

    @Override
    public void handshake(ServerConnectionHandler server) throws RemoteException {
        //not needed in socket programming
    }

    final Object sendLock = new Object();

    final Object receiveLock = new Object();


    @Override
    public void send(NetworkMessage networkMessage) throws RemoteException {
        synchronized (sendLock) {
            try {
                oos.writeObject(networkMessage);
                oos.flush();
                oos.reset();
            } catch (IOException e) {
                throw new RemoteException("Cannot send network message", e);
            }
        }
    }

    @Override
    public void lifeline() throws RemoteException {
        //not needed in socket programming
    }

    public void receive(ServerConnectionHandler serverConnectionHandler) throws IOException {
        synchronized (receiveLock) {
            try {
                NetworkMessage message = (NetworkMessage) ois.readObject();
                serverConnectionHandler.send(message);
            } catch (ClassNotFoundException ignored) {
                //ignoring out of date messages
            }
        }
    }
}
