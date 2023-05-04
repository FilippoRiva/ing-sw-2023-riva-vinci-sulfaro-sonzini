package it.polimi.myshelfie;

import it.polimi.myshelfie.network.*;
import it.polimi.myshelfie.utils.Observer;
import it.polimi.myshelfie.view.ViewBuffer;
import it.polimi.myshelfie.view.ViewUpdate;
import it.polimi.myshelfie.view.graphical.GraphicalUI;
import it.polimi.myshelfie.view.textual.CLIInputReader;
import it.polimi.myshelfie.view.textual.TextualUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ClientApp {
    static String host = "localhost";
    static int registryPort = 1099;
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int networkChoice;
        int viewChoice;
        try{
            System.out.println("Choose between socket(1) and RMI(2):");
            networkChoice = scanner.nextInt();
            System.out.println("Choose between CLI(1) and Graphical UI(2)");
            viewChoice = scanner.nextInt();
        } catch (InputMismatchException e){
            System.err.println("Invalid Option");
            return;
        }
        if(networkChoice > 2 || networkChoice < 1 || viewChoice >2 || viewChoice < 1) {
            System.err.println("Invalid Option");
            return;
        }
        ViewBuffer viewBuffer = new ViewBuffer();
        List<String> inputParameters = new ArrayList<>();
        Observer<ViewUpdate> view;
        if(viewChoice == 1) view = new TextualUI(viewBuffer,inputParameters);
        else view = new GraphicalUI();
        viewBuffer.addUI(view);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            if(networkChoice == 1) startSocketConnection(viewBuffer);
            else startRMIConnection(viewBuffer);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(viewChoice==1) new Thread(()->new CLIInputReader(in, (TextualUI) view,viewBuffer,inputParameters)).start();
    }

    private static void startRMIConnection(ViewBuffer viewBuffer) throws RemoteException {
        Registry registry;
        registry = LocateRegistry.getRegistry(host,registryPort);
        Server server;
        try {
            server = (Server) registry.lookup("server");
        } catch (NotBoundException e) {
            throw new RemoteException("", e);
        }
        server.connect(new ClientImpl(viewBuffer));
    }

    private static void startSocketConnection(ViewBuffer viewBuffer) throws RemoteException {
        ServerConnectionHandlerStub serverStub = new ServerConnectionHandlerStub(host, 54321);
        ClientImpl client = new ClientImpl(viewBuffer);
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
