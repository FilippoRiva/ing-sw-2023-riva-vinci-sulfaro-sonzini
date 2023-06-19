package it.polimi.myshelfie;

import it.polimi.myshelfie.network.*;
import it.polimi.myshelfie.view.ViewManager;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.textual.ANSI;
import it.polimi.myshelfie.view.textual.TextualUI;

import java.io.IOException;
import java.io.InvalidClassException;
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
    static String clientIP = "";
    static int registryPort = 1099;
    public static void main(String[] args){
        Runtime.getRuntime().addShutdownHook(new Thread(()-> System.out.println(ANSI.CLEAR_ALL)));
        initialUserChoice();
    }

    public static void initialUserChoice(){
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
        while(args[0]==null && !connected){
            System.out.println("--------------------------------------------------------");
            System.out.print("> Input server IP or press enter to connect to localhost : ");
            String ip = scanner.nextLine();
            try {
                if(!InetAddress.getByName(ip).isReachable(1000)){
                    System.out.println("   !! Not a reachable ip hostname");
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

        if(viewChoice == 1) {
            TextualUI view = new TextualUI();
            viewManager.addUI(view);
        }
        if(args[1]!=null) clientIP = args[1];
        if(args[0]!=null){
            serverIP = args[0];
            try{
                if(networkChoice == 1) startSocketConnection(viewBuffer);
                else startRMIConnection(viewBuffer);
            } catch (RemoteException e){
                throw new RuntimeException(e);
            }
        }

        List<String> inputParameters = new ArrayList<>();
        Observer<ViewUpdate, Object> view;
        if(viewChoice == 1) view = new TextualUI(viewBuffer,inputParameters);
        else view = new GraphicalUI();
        viewBuffer.addUI(view);
        if(viewChoice==1) new Thread(()->new CLIInputReader((TextualUI) view,viewBuffer,inputParameters)).start();
        else {
            GraphicalUI view = new GraphicalUI();
            viewManager.addUI(view);
            view.startGUI();
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
    private static void startRMIConnection(ViewBuffer viewBuffer) throws RemoteException {
        if(clientIP.equals("")) {
            try {
                Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                for (NetworkInterface net : Collections.list(netInterfaces)) {
                    if (net.isUp() && !net.isLoopback()) {
                        for (InetAddress addr : Collections.list(net.getInetAddresses())) {
                            if (!addr.getHostAddress().contains(":")) { //only IPv4
                                clientIP = addr.getHostAddress();
                                break;
                            }
                        }
                    }
                }
                if (clientIP.equals("")) clientIP = "localhost";
            } catch (SocketException e) {
                throw new RuntimeException("Unable to get Network Interfaces");
            }
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
                    try {
                        serverStub.close();
                    } catch (RemoteException ignored) {
                    }
                    break;
                } catch (InvalidClassException ignored){
                    //receiving different class versions
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            viewManager.stopUI();
        }).start();
    }
}
