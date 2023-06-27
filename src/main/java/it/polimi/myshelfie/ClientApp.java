package it.polimi.myshelfie;

import it.polimi.myshelfie.network.*;
import it.polimi.myshelfie.utils.NetworkInterfaceProvider;
import it.polimi.myshelfie.view.UI;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.TextualUI;

import java.io.IOException;
import java.io.InvalidClassException;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClientApp {
    static String serverIP = "localhost";
    static String clientIP = "";
    static final int registryPort = 1099;

    public static void main(String[] args) {
        for(int i = 0; i <args.length; i++){
            if(args[i].equals("--client")){
                i++;
                if(args.length==i) {
                    System.out.println("please insert client IP");
                    return;
                }
                if(!args[i].equals("")){
                    try {
                        if (!InetAddress.getByName(args[i]).isReachable(1000)) {
                            System.out.println("   !! The specified client IP is not reachable. Please enter your IP.");
                            return;
                        } else {
                            System.out.println("Client IP set : " +args[i]);
                            clientIP = args[i];
                        }
                    } catch (IOException e) {
                        System.out.println("   !! Unknown client host name. Please enter your IP.");
                        return;
                    }
                }
            } else if (args[i].equals("--server")) {
                i++;
                if(args.length==i) {
                    System.out.println("please insert server IP");
                    return;
                }
                if(!args[i].equals("")) {
                    try {
                        if (!InetAddress.getByName(args[i]).isReachable(1000)) {
                            System.out.println("   !! The specified server IP is not reachable.");
                        } else {
                            System.out.println("Server IP set : " + args[i]);
                            serverIP = args[i];
                        }
                    } catch (IOException e) {
                        System.out.println("   !! The specified server host name is unknown.");
                    }
                }
            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println(ANSI.CLEAR_ALL)));
        initialUserChoice();
    }

    public static void initialUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int networkChoice = -1;
        int viewChoice = -1;

        while (networkChoice == -1) {
            System.out.println("--------------------------------------------------------");
            System.out.print("> Choose between socket(1) and RMI(2) : ");
            networkChoice = getChoice(scanner);
        }

        while (viewChoice == -1) {
            System.out.println("--------------------------------------------------------");
            System.out.print("> Choose between CLI(1) and Graphical UI(2) : ");
            viewChoice = getChoice(scanner);
        }

        ViewManager viewManager = ViewManager.getInstance();

        boolean connected = false;
        if(!serverIP.equals("localhost")) {
            try {
                if (networkChoice == 1) startSocketConnection(viewManager);
                else startRMIConnection(viewManager);
                connected = true;
            } catch (IOException e) {
                System.out.println("   !! Unable to connect using cli parameters " + serverIP);
            }
        }
        while (!connected) {
            String ip;
            System.out.println("--------------------------------------------------------");
            System.out.print("> Input server IP or press enter to connect to localhost : ");
            ip = scanner.nextLine();
            try {
                if (!InetAddress.getByName(ip).isReachable(1000)) {
                    System.out.println("   !! Not a reachable ip hostname");
                } else {
                    if (!ip.equals("")) serverIP = ip;
                    if (networkChoice == 1) startSocketConnection(viewManager);
                    else startRMIConnection(viewManager);
                    connected = true;
                }
            } catch (IOException e) {
                System.out.println("   !! Unable to connect to " + serverIP);
            }
        }

        UI view;
        if (viewChoice == 1) view = new TextualUI();
        else view = new GraphicalUI();
        viewManager.addUI(view);
        view.startUI();
    }

    private static int getChoice(Scanner scanner) {
        int networkChoice;
        try {
            networkChoice = Integer.parseInt(scanner.nextLine());
            if (!(networkChoice == 1 || networkChoice == 2)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ignored) {
            System.out.println("   !! Not a valid choice");
            return -1;
        }
        return networkChoice;
    }

    private static void startRMIConnection (ViewManager viewManager) throws RemoteException {
        if (clientIP.equals("")) { //automatic client ip retrieval
            clientIP = NetworkInterfaceProvider.getInterfaceHostName();
        }
        System.out.println("RMI started over : " + clientIP);
        System.setProperty("java.rmi.server.hostname", clientIP); //the property is still called server
        Registry registry;
        registry = LocateRegistry.getRegistry(serverIP, registryPort);
        Server server;
        try {
            server = (Server) registry.lookup("server");
        } catch (NotBoundException e) {
            throw new RemoteException("", e);
        }
        server.connect(new ClientImpl(viewManager));
    }

    private static void startSocketConnection (ViewManager viewManager) throws RemoteException {
        ServerConnectionHandlerStub serverStub = new ServerConnectionHandlerStub(serverIP, 54321);
        ClientImpl client = new ClientImpl(viewManager);
        client.handshake(serverStub);
        new Thread(() -> {
            boolean running = true;
            while (running) {
                try {
                    serverStub.receive(client);
                } catch (RemoteException e) {
                    try {
                        serverStub.close();
                    } catch (RemoteException ignored) {
                    }
                    break;
                } catch (StreamCorruptedException e){
                    System.err.println("The stream has been reset by the server");
                } catch (InvalidClassException ignored) {
                    System.out.println("Receiving Unrecognized Classes");
                } catch (SocketException e){
                    System.err.println("Lost server connection. Disconnecting....");
                    running = false;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            viewManager.stopUI();
        }).start();
    }
}


