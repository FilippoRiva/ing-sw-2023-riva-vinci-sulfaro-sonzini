package it.polimi.myshelfie;

import it.polimi.myshelfie.network.*;
import it.polimi.myshelfie.view.ViewBuffer;
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
import java.util.List;

public class ClientApp {
    static String host = "localhost";
    static int registryPort = 1099;
    public static void main(String[] args){
        ViewBuffer viewBuffer = new ViewBuffer();
        List<String> inputParameters = new ArrayList<>();
        TextualUI view = new TextualUI(viewBuffer,inputParameters);
        viewBuffer.addUI(view);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        new Thread(()->new CLIInputReader(in,view,viewBuffer,inputParameters)).start();
        try {
            //startRMIConnection(viewBuffer);
            startSocketConnection(viewBuffer);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
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
