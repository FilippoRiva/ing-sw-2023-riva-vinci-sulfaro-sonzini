package it.polimi.myshelfie;

import it.polimi.myshelfie.controller.ControllerImpl;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.model.ModelImpl;
import it.polimi.myshelfie.network.ClientSkeleton;
import it.polimi.myshelfie.network.ServerConnectionHandlerImpl;
import it.polimi.myshelfie.network.ServerImpl;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ServerApp {
    public static void main (String[] args){
        System.out.println("Server starting up");
        ControllerImpl controller = new ControllerImpl();
        ModelImpl model = new ModelImpl();
        controller.setModel(model);
        ServerImpl server;
        try {
            server = new ServerImpl(controller);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        new Thread(()->startRMI(server)).start();
        new Thread(()->startSocket(controller)).start();
    }
    public static void startRMI(ServerImpl server){
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e){
            try {
                registry = LocateRegistry.getRegistry();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            registry.rebind("server",server);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("RMI up and running");
    }

    public static void startSocket(Controller controller) {
        try (ServerSocket serverSocket = new ServerSocket(54321)) {
            System.out.println("Socket up and running");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected "+socket.getInetAddress());
                new Thread(() -> {
                    ServerConnectionHandlerImpl serverConnectionHandler = null;
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        serverConnectionHandler = new ServerConnectionHandlerImpl(clientSkeleton, controller);
                        serverConnectionHandler.start();
                        while (true) {
                            clientSkeleton.receive(serverConnectionHandler);
                        }
                    } catch (IOException e) {
                        System.err.println("Cannot receive from client. Closing this connection...");
                    } finally {
                        try {
                            assert serverConnectionHandler != null;
                            serverConnectionHandler.close();
                        } catch (RemoteException ex) {
                            throw new RuntimeException("Unable to close client connection", ex);
                        }
                        System.out.println("Client Disconnected");
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot start socket server", e);
        }
    }
}
