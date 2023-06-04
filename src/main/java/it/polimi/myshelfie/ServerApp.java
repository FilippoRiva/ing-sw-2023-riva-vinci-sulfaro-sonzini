package it.polimi.myshelfie;

import it.polimi.myshelfie.controller.ControllerImpl;
import it.polimi.myshelfie.controller.interfaces.Controller;
import it.polimi.myshelfie.model.ModelImpl;
import it.polimi.myshelfie.network.ClientSkeleton;
import it.polimi.myshelfie.network.ServerConnectionHandlerImpl;
import it.polimi.myshelfie.network.ServerImpl;

import java.io.IOException;
import java.io.InvalidClassException;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;
import java.util.Enumeration;


public class ServerApp {
    public static void main (String[] args){
        String serverIP = "";
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface net : Collections.list(netInterfaces)){
                if(net.isUp() && !net.isLoopback()) {
                    for(InetAddress addr : Collections.list(net.getInetAddresses())){
                        if(!addr.getHostAddress().contains(":")){ //only IPv4
                            serverIP = addr.getHostAddress();
                            break;
                        }
                    }
                }
            }
            if(serverIP.equals("")) serverIP = "localhost";
        } catch (SocketException e) {
            throw new RuntimeException("Unable to get Network Interfaces");
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
        System.out.println("RMI up and running");
    }

    public static void startSocket(Controller controller) {
        try (ServerSocket serverSocket = new ServerSocket(54321)) {
            System.out.println("Socket up and running");
            while (true) {
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
                        System.err.println("Cannot receive from client. Closing connection...");
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
