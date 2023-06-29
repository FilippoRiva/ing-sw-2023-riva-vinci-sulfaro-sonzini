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
     * @throws RemoteException we cannot get the object output/input streams
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

    /**
     * {@inheritDoc}
     * @return the socket address
     */
    @Override
    public String getHostName() {
        return socket.getInetAddress().getHostAddress();
    }

    /**
     * Used only in RMI implementation
     */
    @Override
    public void handshake(ServerConnectionHandler server){
        //not needed in socket programming
    }

    /**
     * {@inheritDoc}
     * @param networkMessage the message to send.
     * @throws RemoteException if we cannot send the network message over the object output stream
     */
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

    /**
     * Used only in RMI implementation
     */
    @Override
    public void lifeline() {
        //not needed in socket programming
    }

    /**
     * Used to receive messages
     *
     * @param serverConnectionHandler the server connection handler.
     * @throws IOException if the object input stream read fails.
     */
    public void receive(ServerConnectionHandler serverConnectionHandler) throws IOException {
        synchronized (receiveLock) {
            try {
                NetworkMessage message = (NetworkMessage) ois.readObject();
                serverConnectionHandler.send(message);
            } catch (ClassNotFoundException ignored) {
                ServerLogger.logClientInfo((ServerConnectionHandlerImpl) serverConnectionHandler,"Receiving unrecognized messages. ");
            }
        }
    }
}
