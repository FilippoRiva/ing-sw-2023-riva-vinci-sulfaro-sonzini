package it.polimi.myshelfie;

import it.polimi.myshelfie.controller.ControllerImpl;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.model.ModelImpl;
import it.polimi.myshelfie.network.ClientSkeleton;
import it.polimi.myshelfie.network.ServerConnectionHandlerImpl;
import it.polimi.myshelfie.network.ServerImpl;
import it.polimi.myshelfie.utils.NetworkInterfaceProvider;
import it.polimi.myshelfie.utils.ServerLogger;

import java.io.IOException;
import java.io.InvalidClassException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;


/**
 * Used to run the Server.
 */
public class ServerApp {
    public static void main(String[] args) {
        ServerLogger.initialize();
        String serverIP = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--rmi")) {
                i++;
                if (i == args.length) {
                    System.out.println("Please insert rmi parameter");
                    return;
                }
                try {
                    if (!InetAddress.getByName(args[i]).isReachable(1000)) {
                        System.out.println("   !! The specified server IP is not reachable. Please enter your IP.");
                        return;
                    } else {
                        serverIP = args[i];
                    }
                } catch (IOException e) {
                    System.out.println("   !! Unknown server host name");
                    return;
                }
            }
        }
        if (serverIP.equals("")) {
            serverIP = NetworkInterfaceProvider.getInterfaceHostName();
        }
        System.out.println("Server starting up over : " + serverIP);
        System.setProperty("java.rmi.server.hostname", serverIP);
        ControllerImpl controller = new ControllerImpl();
        ModelImpl model = new ModelImpl();
        controller.setModel(model);
        ServerImpl server;
        try {
            server = new ServerImpl(controller);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> startRMI(server)).start();
        new Thread(() -> startSocket(controller)).start();
    }


    /**
     * Starts RMI connection.
     * @param server the server.
     */
    public static void startRMI(ServerImpl server) {
        Registry registry;
        final int port = 1099;

        try {
            registry = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            try {
                registry = LocateRegistry.getRegistry();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        try {
            registry.rebind("server", server);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println("RMI server up and running");
        System.out.println("--------------------------------------------------------");
    }


    /**
     * Starts socket connection.
     * @param controller the controller.
     */
    public static void startSocket(Controller controller) {
        final int port = 54321;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Socket server up and running");
            boolean running = true;
            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        ServerConnectionHandlerImpl serverConnectionHandler = null;
                        try {
                            ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                            serverConnectionHandler = new ServerConnectionHandlerImpl(clientSkeleton, controller);
                            serverConnectionHandler.start();
                            while (true) {
                                clientSkeleton.receive(serverConnectionHandler);
                            }
                        } catch (InvalidClassException e){
                            ServerLogger.logClientInfo(serverConnectionHandler,"Receiving unknown messages from client. Forcing Disconnection");
                        } catch (IOException e) {
                            ServerLogger.log(Level.FINER, "Socket receive failed from " + socket.getInetAddress().getHostAddress());
                        } finally {
                            assert serverConnectionHandler != null;
                            serverConnectionHandler.close();
                            ServerLogger.logClientInfo(serverConnectionHandler, "Socket Closed");
                            try {
                                socket.close();
                            } catch (IOException e) {
                                ServerLogger.log(Level.WARNING, "Cannot close socket \"" + serverConnectionHandler.hostName + "\"");
                            }
                        }
                    }).start();
                } catch (IOException e) { //caught from ServerSocket accept
                    ServerLogger.log(Level.SEVERE, "Socket server failed");
                    running = false;
                }
            }
        } catch (IOException e) { //caught from ServerSocket constructor
            ServerLogger.log(Level.WARNING, "Unable to start socket server. The requested socket is already being used");
        }
    }
}
