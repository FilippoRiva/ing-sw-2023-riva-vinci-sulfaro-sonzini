package it.polimi.myshelfie;

import it.polimi.myshelfie.controller.ControllerImpl;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.model.ModelImpl;
import it.polimi.myshelfie.network.ClientSkeleton;
import it.polimi.myshelfie.network.ServerConnectionHandlerImpl;
import it.polimi.myshelfie.network.ServerImpl;
import it.polimi.myshelfie.utils.NetworkInterfaceProvider;

import java.io.IOException;
import java.io.InvalidClassException;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ServerApp {
    public static void main (String[] args){
        String serverIP = "";
        for(int i = 0; i < args.length; i++){
            if(args[i].equals("--rmi")){
                i++;
                if(i==args.length){
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
        if(serverIP.equals("")){
            serverIP = NetworkInterfaceProvider.getInterfaceHostName();
        }
        System.out.println("Server starting up over : "+serverIP);
        System.setProperty("java.rmi.server.hostname",serverIP);
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
        System.out.println("RMI server up and running");
    }

    public static void startSocket(Controller controller) {
        try (ServerSocket serverSocket = new ServerSocket(54321)) {
            System.out.println("Socket server up and running");
            boolean running = true;
            while (running) {
                try{
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
                        } catch (InvalidClassException ignored){
                            System.err.println("Receiving unknown messages from client.");
                        } catch (IOException e) {
                            assert serverConnectionHandler != null;
                            System.out.println(serverConnectionHandler.hostName + " | ID : " + serverConnectionHandler.clientID + " Closing Socket");
                        } finally {
                            try {
                                assert serverConnectionHandler != null;
                                serverConnectionHandler.close();
                            } catch (RemoteException ignored) {
                                System.out.println(serverConnectionHandler.hostName+" | ID : "+serverConnectionHandler.clientID+"Unable to close a Connection Handler after disconnection");
                            }
                            System.out.println(serverConnectionHandler.hostName + " | ID : " + serverConnectionHandler.clientID + " Socket Closed");
                            try {
                                socket.close();
                            } catch (IOException e) {
                                System.out.println("Cannot close socket");
                            }
                        }
                    }).start();
                } catch (IOException e){ //caught from ServerSocket accept
                    System.out.println("Socket server failed");
                    running = false;
                }
            }
        } catch (IOException e) { //caught from ServerSocket constructor
            System.out.println("Unable to start socket server. The requested socket is already being used");
        }
    }
}
