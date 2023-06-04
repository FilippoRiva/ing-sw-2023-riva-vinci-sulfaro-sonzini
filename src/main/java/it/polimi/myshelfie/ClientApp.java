package it.polimi.myshelfie;

import it.polimi.myshelfie.network.*;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.ViewUpdate;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.textual.CLIInputReader;
import it.polimi.myshelfie.view.textual.TextualUI;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClientApp {
    static String serverIP = "localhost";
    static int registryPort = 1099;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int networkChoice = -1;
        int viewChoice = -1;

        while(networkChoice == -1){
            System.out.println("--------------------------------------------------------");
            System.out.print("> Choose between socket(1) and RMI(2) : ");
            try{
                networkChoice = Integer.parseInt(scanner.nextLine());
                if(!(networkChoice== 1 || networkChoice == 2)){
                    networkChoice = -1;
                }
            } catch (NumberFormatException ignored){
            }
            if(networkChoice == -1){
                System.out.println("   !! Not a valid choice");
            }
        }

        while(viewChoice == -1){
            System.out.println("--------------------------------------------------------");
            System.out.print("> Choose between CLI(1) and Graphical UI(2) : ");
            try{
                viewChoice = Integer.parseInt(scanner.nextLine());
                if(!(viewChoice == 1 || viewChoice == 2)){
                    viewChoice = -1;
                }
            } catch (NumberFormatException ignored){
            }
            if(viewChoice == -1){
                System.out.println("   !! Not a valid choice");
            }
        }

        ViewManager viewManager = ViewManager.getInstance();

        boolean connected = false;
        while(!connected){
            System.out.println("--------------------------------------------------------");
            System.out.print("> Input server IP or press enter to connect to localhost : ");
            String ip = scanner.nextLine();
            try {
                if(!ip.equals("") && !InetAddress.getByName(ip).isReachable(500)){
                    System.out.println("   !! Not a valid hostname");
                } else {
                    if(!ip.equals("")) serverIP = ip;
                    if(networkChoice == 1) startSocketConnection(viewManager);
                    else startRMIConnection(viewManager);
                    connected = true;
                }
            } catch (IOException e) {
                System.out.println("   !! Unable to connect to "+serverIP);
            }
        }

        List<String> inputParameters = new ArrayList<>();
        Observer<ViewUpdate, Object> view;
        if(viewChoice == 1) view = new TextualUI(viewManager,inputParameters);
        else view = new GraphicalUI();
        viewManager.addUI(view);
        if(viewChoice==1) new Thread(()->new CLIInputReader((TextualUI) view, viewManager,inputParameters)).start();
        else {
            ((GraphicalUI)view).startGUI();
        }
    }

    private static void startRMIConnection(ViewManager viewManager) throws RemoteException {
        String clientIP = "";
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface net : Collections.list(netInterfaces)){
                if(net.isUp() && !net.isLoopback()) {
                    for(InetAddress addr : Collections.list(net.getInetAddresses())){
                        if(!addr.getHostAddress().contains(":")){ //only IPv4
                            clientIP = addr.getHostAddress();
                            break;
                        }
                    }
                }
            }
            if(clientIP.equals("")) clientIP = "localhost";
        } catch (SocketException e) {
            throw new RuntimeException("Unable to get Network Interfaces");
        }
        System.out.println("RMI started over : "+clientIP);
        System.setProperty("java.rmi.server.hostname",clientIP); //the property is still called server
        Registry registry;
        registry = LocateRegistry.getRegistry(serverIP,registryPort);
        Server server;
        try {
            server = (Server) registry.lookup("server");
        } catch (NotBoundException e) {
            throw new RemoteException("", e);
        }
        server.connect(new ClientImpl(viewManager));
    }

    private static void startSocketConnection(ViewManager viewManager) throws RemoteException {
        ServerConnectionHandlerStub serverStub = new ServerConnectionHandlerStub(serverIP, 54321);
        ClientImpl client = new ClientImpl(viewManager);
        client.handshake(serverStub);
        new Thread(() -> {
            while(true) {
                try {
                    serverStub.receive(client);
                } catch (RemoteException e) {
                    System.err.println("Cannot receive from server. Stopping...");
                    try {
                        serverStub.close();
                    } catch (RemoteException ex) {
                        System.err.println("Cannot close connection with server. Halting...");
                    }
                    System.exit(1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
