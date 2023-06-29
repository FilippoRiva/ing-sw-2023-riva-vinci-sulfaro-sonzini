package it.polimi.myshelfie.network;

import it.polimi.myshelfie.controller.interfaces.ChatController;
import it.polimi.myshelfie.controller.interfaces.GameController;
import it.polimi.myshelfie.controller.interfaces.LobbyController;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Represents the Server Connection Handler implementation for Socket connection.
 */
public class ServerConnectionHandlerStub implements ServerConnectionHandler {
    final String ip;
    final int port;
    final Object receiveLock = new Object();
    final Object sendLock = new Object();
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private final Socket socket;

    /**
     * Constructor
     *
     * @param ip   the ip.
     * @param port the port.
     * @throws RemoteException
     */
    public ServerConnectionHandlerStub(String ip, int port) throws RemoteException {
        this.ip = ip;
        this.port = port;
        try {
            this.socket = new Socket(ip, port);
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
        } catch (IOException e) {
            throw new RemoteException("Unable to connect to the server", e);
        }
    }

    @Override
    public void send(NetworkMessage networkMessage) throws RemoteException {
        synchronized (sendLock) {
            try {
                oos.writeObject(networkMessage);
                oos.flush();
                oos.reset();
            } catch (IOException e) {
                throw new RemoteException("Cannot send message", e);
            }
        }
    }

    @Override
    public void setChatController(ChatController chatController) throws RemoteException {
    }

    @Override
    public void setLobbyController(LobbyController lobbyController) throws RemoteException {
    }

    @Override
    public void lifeline() throws RemoteException {
        //not needed in socket implementation
    }

    /**
     * Used to receive messages.
     *
     * @param client the client.
     * @throws IOException
     */
    public void receive(ClientImpl client) throws IOException {
        synchronized (receiveLock) {
            try {
                NetworkMessage message = (NetworkMessage) ois.readObject();
                client.send(message);
            } catch (ClassNotFoundException ignored) {
                System.out.println("Receiving Unrecognized Classes");
            } catch (EOFException e) {
                throw new RemoteException();
            }
        }
    }

    @Override
    public void close() throws RemoteException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }
    }

    @Override
    public void ping() {
        //not needed in socket implementation
    }

    @Override
    public GameController getGameController() {
        return null;
    }

    @Override
    public void setGameController(GameController gameController) throws RemoteException {
    }
}
