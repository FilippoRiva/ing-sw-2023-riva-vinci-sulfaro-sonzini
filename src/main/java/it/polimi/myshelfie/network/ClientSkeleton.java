package it.polimi.myshelfie.network;

import it.polimi.myshelfie.utils.ServerLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Represents the Client implementation for Socket connection.
 */
public class ClientSkeleton implements Client {
    final Object sendLock = new Object();
    final Object receiveLock = new Object();
    private final Socket socket;
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    /**
     * Constructor.
     *
     * @param socket the socket.
     * @throws RemoteException
     */
    public ClientSkeleton(Socket socket) throws RemoteException {
        this.socket = socket;
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
    public String getHostName() {
        return socket.getInetAddress().getHostAddress();
    }

    @Override
    public void handshake(ServerConnectionHandler server) throws RemoteException {
        //not needed in socket programming
    }

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
            sendLock.notifyAll();
        }
    }

    @Override
    public void lifeline() throws RemoteException {
        //not needed in socket programming
    }

    /**
     * Used to receive messages
     *
     * @param serverConnectionHandler the server connection handler.
     * @throws IOException
     */
    public void receive(ServerConnectionHandler serverConnectionHandler) throws IOException {
        synchronized (receiveLock) {
            try {
                NetworkMessage message = (NetworkMessage) ois.readObject();
                serverConnectionHandler.send(message);
            } catch (ClassNotFoundException ignored) {
                ServerLogger.logClientInfo((ServerConnectionHandlerImpl) serverConnectionHandler,"Receiving unrecognized messages. ");
            }
            receiveLock.notifyAll();
        }
    }
}
